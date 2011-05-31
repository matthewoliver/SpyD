/**
* This file is part of spyd.
*
* Spyd is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Spyd is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Network Manifest Checker.  If not, see <http://www.gnu.org/licenses/>.
* 
* @author Matthew Oliver
*/
package au.gov.naa.digipres.spyd.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import au.gov.naa.digipres.spyd.preferences.SpydPreferences;

public class EmailerThread extends Thread {
	private static final int MAXIMUM_MESSAGE_QUEUE_SIZE = 50;
	private static final String ACTION_SUCCESSFUL_SMTP_CODE = "250";
	private static final String CONNECTION_CLOSING_SMTP_CODE = "221";
	public static final int WAIT_BETWEEN_SMTP_CHECKS = 300000; //5 minutes
	public static final int WAIT_BETWEEN_EMPTY_QUEUE_CHECKS = 5000; //5 seconds
	public static final String DEFAULT_FROM_ADDRESS = "no-reply@spyd.local.lan";
	public static final String DEFAULT_TO_ADDRESS = "postmaster@localhost";

	private Session emailSession;
	private InternetAddress fromAddress;
	private List<InternetAddress> toAddresses;
	private String smtpHost;

	private CommunicationManager communicationManager;
	private Logger logger = communicationManager.getClassLogger(this);
	private SpydPreferences preferences = communicationManager.getPluginManager().getPreferenceManager().getPreferences();

	private Queue<MimeMessage> messageQueue;

	private boolean running;
	private boolean emailDisabled;

	public EmailerThread(CommunicationManager communicationManager) throws EmailException {
		this.communicationManager = communicationManager;

		running = false;
		emailDisabled = false;

		// Set the from address
		String fromAddressStr = preferences.getPreference(SpydPreferences.EMAIL_FROM_ADDRESS);
		if (fromAddressStr == null) {
			fromAddressStr = DEFAULT_FROM_ADDRESS;
		}
		try {
			fromAddress = new InternetAddress(fromAddressStr);
		} catch (AddressException e) {
			logger.warning(fromAddressStr + " is not a valid address, using default: '" + DEFAULT_FROM_ADDRESS + "'");
			try {
				fromAddress = new InternetAddress(DEFAULT_FROM_ADDRESS);
			} catch (AddressException e1) {
				logger.severe("Cannot set a from address.");
				emailDisabled = true;
			}
		}

		// Set the to addresses
		String toAddressStr = preferences.getPreference(SpydPreferences.EMAIL_TO_ADDRESS);
		if (toAddressStr == null) {
			toAddressStr = DEFAULT_FROM_ADDRESS;
		}
		// load the to addresses into the toAddresses list.
		toAddresses = new Vector<InternetAddress>();

		for (String address : toAddressStr.split(",")) {
			InternetAddress intAddress;
			try {
				intAddress = new InternetAddress(address.trim());
			} catch (AddressException ex) {
				logger.warning("To address '" + address + "' is not a valid address");
				continue;
			}
			toAddresses.add(intAddress);
		}

		if (toAddresses.size() == 0) {
			logger.severe("Connot set a to address.");
			emailDisabled = true;
		}

		messageQueue = new ArrayBlockingQueue<MimeMessage>(MAXIMUM_MESSAGE_QUEUE_SIZE);

		//attempt to setup the email session
		smtpHost = preferences.getPreference(SpydPreferences.SMTP_SERVER);
		String smtpPort = preferences.getPreference(SpydPreferences.SMTP_PORT);

		if ((smtpHost == null) || (smtpPort == null)) {
			emailDisabled = true;
			logger.warning("Emailing disabled, smtp.server and smtp.port must be set in the propertie/config file.");
		}
		if (!emailDisabled) {
			Properties emailServerSettings = new Properties();
			emailServerSettings.setProperty("mail.smtp.host", smtpHost);
			emailServerSettings.setProperty("mail.smtp.port", smtpPort);
			emailSession = Session.getInstance(emailServerSettings);
		} else {
			throw new EmailException("Email disabled due to email setup errors.");
		}
	}

	/**
	 * Tests that the SMTP server is up and ready to send an email message.
	 * A dummy message is constructed on the server using a socket connection and the standard SMTP commands.
	 * If the message construction is successful (ie does not throw an exception) then the server is working
	 * correctly, and the dummy message can be discarded without actually sending it.
	 * 
	 * @return true if the server is ready to send email messages. This method never returns false, an exception is thrown
	 * if the server is down or not ready.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private boolean checkSMTPServerStatus() throws UnknownHostException, IOException {
		Socket socket;
		BufferedReader socketReader;
		PrintWriter socketWriter;
		socket = new Socket();

		// Connect to the SMTP server
		InetSocketAddress address = new InetSocketAddress(smtpHost, 25);
		socket.connect(address);
		socket.setSoTimeout(2000);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));
		socketWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

		// Read in the server's initialisation line
		socketReader.readLine();

		// Construct dummy message
		String heloMessage = "HELO spyd.local.lan";
		socketWriter.println(heloMessage);
		String heloResponse = socketReader.readLine();
		if (heloResponse == null || !heloResponse.startsWith(ACTION_SUCCESSFUL_SMTP_CODE)) {
			throw new IOException("HELO action unsuccessful: " + heloResponse);
		}
		String fromMessage = "MAIL FROM:" + fromAddress.getAddress();
		socketWriter.println(fromMessage);
		String fromResponse = socketReader.readLine();
		if (fromResponse == null || !fromResponse.startsWith(ACTION_SUCCESSFUL_SMTP_CODE)) {
			throw new IOException("MAIL FROM action unsuccessful: " + fromResponse);
		}
		String rcptToMessage = "RCPT TO:" + toAddresses.get(0).getAddress();
		socketWriter.println(rcptToMessage);
		String rcptToResponse = socketReader.readLine();
		if (rcptToResponse == null || !rcptToResponse.startsWith(ACTION_SUCCESSFUL_SMTP_CODE)) {
			throw new IOException("RCPT TO action unsuccessful: " + rcptToResponse);
		}
		String quitMessage = "QUIT";
		socketWriter.println(quitMessage);
		String quitResponse = socketReader.readLine();
		if (quitResponse == null || !quitResponse.startsWith(CONNECTION_CLOSING_SMTP_CODE)) {
			throw new IOException("QUIT action unsuccessful: " + quitResponse);
		}

		// If we have reached this point then the server is ready to send email messages.
		return true;

	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		running = true;

		while (running) {
			if (emailDisabled) {
				running = false;
				break;
			}

			// Get the first message in the queue, without removing it from the queue
			MimeMessage message = messageQueue.peek();

			// If the queue is not empty
			if (message != null) {
				try {
					// Attempt to send the message
					Transport.send(message);

					// The message was successfully sent, so remove it from the queue
					messageQueue.remove();
				} catch (MessagingException mex) {
					logger.log(Level.SEVERE, "Problem sending message", mex);

					// Message sending has failed, check the SMTP server every 5 minutes until it is back up
					while (running) {

						// Wait 5 minutes then try again
						try {
							Thread.sleep(WAIT_BETWEEN_SMTP_CHECKS);
						} catch (InterruptedException iex) {
							// Nothing to do
						}

						try {
							if (checkSMTPServerStatus()) {
								// SMTP server is working again
								break;
							}
						} catch (Exception ex) {
							// SMTP server is still down, log the exception and restart the loop.
							logger.log(Level.SEVERE, "SMTP server is not working!", mex);
						}
					}
				}

			} else {
				try {
					// The queue is empty, check again in 5 seconds' time
					Thread.sleep(WAIT_BETWEEN_EMPTY_QUEUE_CHECKS);
				} catch (InterruptedException iex) {
					// Nothing to do
				}
			}
		}

	}

	/**
	 * Sends an email.
	 * @param subject The subject of the email
	 * @param message The message of the email
	 * @throws MessagingException
	 */
	public synchronized void sendEmail(String subject, String message) throws MessagingException {
		if (emailDisabled) {
			logger.warning("Failed to send email: " + subject + " - Email system is disabled, check the email settings and restart the system.");
			return;
		}

		MimeMessage msg = new MimeMessage(emailSession);
		msg.setFrom(fromAddress);
		InternetAddress[] addresses = new InternetAddress[toAddresses.size()];
		toAddresses.toArray(addresses);
		msg.setRecipients(Message.RecipientType.TO, addresses);

		msg.setSubject(subject);
		msg.setText(message);

		messageQueue.offer(msg);
		logger.finer("Sent email: " + subject);
	}

	public boolean isEmailDisabled() {
		return emailDisabled;
	}

	synchronized void preferencesUpdated() throws MessagingException {
		// Get all the email settings from the preferences and see if something relating to this thread has changed.
		// if it has do what is needed to be done.

		// Check the to and from addresses
		// Set the from address
		String fromAddressStr = preferences.getPreference(SpydPreferences.EMAIL_FROM_ADDRESS);
		if (fromAddressStr == null) {
			// disable the email thread
			emailDisabled = true;
			return;
		}

		try {
			fromAddress = new InternetAddress(fromAddressStr);
		} catch (AddressException e) {
			logger.warning(fromAddressStr + " is not a valid address, using default: '" + DEFAULT_FROM_ADDRESS + "'");
			emailDisabled = true;
			return;
		}

		// check the addresses
		String toAddressStr = preferences.getPreference(SpydPreferences.EMAIL_TO_ADDRESS);
		if (toAddressStr == null) {
			// disable the email thread
			emailDisabled = true;
			return;
		}
		// load the to addresses into the toAddresses list.
		toAddresses = new Vector<InternetAddress>();

		for (String address : toAddressStr.split(",")) {
			InternetAddress intAddress;
			try {
				intAddress = new InternetAddress(address.trim());
			} catch (AddressException ex) {
				logger.warning("To address '" + address + "' is not a valid address");
				continue;
			}
			toAddresses.add(intAddress);
		}

		if (toAddresses.size() == 0) {
			logger.severe("Connot set a to address.");
			emailDisabled = true;
		}

		// Now lets check to see if the SMTP server or port has changed? Only if one of them has do we want to recreate the session
		String host = preferences.getPreference(SpydPreferences.SMTP_SERVER);
		String port = preferences.getPreference(SpydPreferences.SMTP_PORT);

		if ((host == null) || port == null) {
			// disable
			emailDisabled = true;
			return;
		}

		// have they changed?
		boolean hasSessionChanged = false;
		if ((!emailSession.getProperty("mail.smtp.host").equals(host)) || (!emailSession.getProperty("mail.smtp.port").equals(port))) {
			Properties emailSettings = new Properties();
			emailSettings.put("mail.smtp.host", host);
			emailSettings.put("mail.smtp.port", port);
			emailSession = Session.getInstance(emailSettings);
		}

		// Update all the emails still in the queue
		Queue<MimeMessage> newQueue = new ArrayBlockingQueue<MimeMessage>(MAXIMUM_MESSAGE_QUEUE_SIZE);
		;
		for (MimeMessage msg : messageQueue) {
			msg.setFrom(fromAddress);

			InternetAddress[] addresses = new InternetAddress[toAddresses.size()];
			toAddresses.toArray(addresses);
			msg.setRecipients(RecipientType.TO, addresses);

			if (hasSessionChanged) {
				MimeMessage newMsg = new MimeMessage(emailSession);
				newMsg.setFrom(msg.getFrom()[0]);
				newMsg.setRecipients(RecipientType.TO, msg.getAllRecipients());
				newMsg.setSubject(msg.getSubject());
				try {
					newMsg.setText(msg.getContent().toString());
				} catch (IOException e) {
					logger.warning("Failed to recreate email: " + msg.getSubject() + " - skipping");
					continue;
				}
				newQueue.offer(newMsg);
			}
		}
		if (hasSessionChanged) {
			messageQueue.clear();
			messageQueue = newQueue;
		}

	}
}
