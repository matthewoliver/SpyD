/**
* This file is part of Network Manifest Checker.
*
* Network Manifest Checker is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Network Manifest Checker is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Network Manifest Checker.  If not, see <http://www.gnu.org/licenses/>.
* 
*/
package au.gov.naa.digipres.spyd.core.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandException;
import au.gov.naa.digipres.spyd.command.CommandListener;
import au.gov.naa.digipres.spyd.command.CommandManager;

public class SpydServerWorker extends Thread {
	final static int BUF_SIZE = 2048;
	static final byte[] EOL = {(byte) '\r', (byte) '\n'};

	// Socket to client we're handling
	private Socket socket;

	private boolean workerRunning = true;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private SpydServer spydServer;

	public SpydServerWorker(SpydServer server) {
		socket = null;
		this.spydServer = server;
	}

	/**
	 * Set the client socket with which this worker will communicate.
	 * This method will activate the worker.
	 * @param socket
	 */
	synchronized void setSocket(Socket socket) {
		this.socket = socket;
		notify();
	}

	/**
	 * Completely shut down this worker.
	 */
	synchronized void stopWorker() {
		workerRunning = false;
		notify();
	}

	/**
	 * Wait until this thread is notified (due to an incoming connection from a client), and then handle the client's request(s).
	 */
	@Override
	public synchronized void run() {
		// This loop is only ended when the stopWorker method is called from an external source.
		while (workerRunning) {
			// The worker is initialised without a reference to a socket, and thus it will enter a waiting state.
			// Once it is given a socket it be notified and will begin execution.
			if (socket == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					return;
				}
			}

			// If the thread has been closed, do nothing further
			if (workerRunning) {
				try {
					handleClient();
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Go back in wait queue if there's fewer than numHandler connections.
				socket = null;
				Vector<SpydServerWorker> workerPool = spydServer.getWorkerPool();
				synchronized (workerPool) {
					if (workerPool.size() >= SpydServer.threadPoolSize) {
						// Too many threads, exit this one
						return;
					}
					workerPool.addElement(this);
				}
			}
		}
	}

	/**
	 * Handle requests or commands from the client. The primary requests are for the current status of the CheckerThread,
	 * and for a list of the results of previous runs of the checksum checker. The client may also send a command
	 * to pause or resume the checker.
	 * 
	 * This method will continue to handle requests as long as the client does not close the connection, or request that the connection be closed.
	 * 
	 * @throws IOException
	 */
	private void handleClient() throws IOException {

		CommandManager commandManager = spydServer.getCommandManager();
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());
		socket.setTcpNoDelay(true);
		String trigger = reader.readLine();

		// we need to create a command listener
		WorkerCommandListener listener = new WorkerCommandListener(ps);
		commandManager.registerCommandListenerToAllCommands(listener);

		while (trigger != null) {
			System.out.println("Received command: " + trigger);

			String[] parameters = trigger.split(" ");
			String lowerCaseTrigger = parameters[0].toLowerCase();

			if (commandManager.hasTrigger(lowerCaseTrigger)) {
				Command command = commandManager.getCommand(lowerCaseTrigger);
				if (commandManager.isQuitCommand(command)) {
					socket.close();
					return;
				} else {
					if (parameters.length > 1) {
						// Some parameters where passed to the command
						for (int i = 1; i < parameters.length; i++) {
							command.addParameter(parameters[i]);
						}
					}

					try {
						command.runCommandWithEndMessage();
					} catch (CommandException e) {
						ps.println("E: " + e.getMessage());
						ps.println("E: Usage: " + command.getUsage());
					}
				}
			} else {
				logger.finest("Received unknown command: " + trigger);
				ps.print("Unknown command: " + trigger + "\r\n");
			}

			trigger = reader.readLine();
		}

		// remove the listener and close the streams
		commandManager.registerCommandListenerToAllCommands(listener);
		ps.flush();
		ps.close();
		reader.close();

	}

	private static class WorkerCommandListener implements CommandListener {

		private PrintStream ps;

		public WorkerCommandListener(PrintStream ps) {
			this.ps = ps;
		}

		@Override
		public void commandEnded() {
		}

		@Override
		public void commandMessage(String message) {
			ps.println(message);
		}

		@Override
		public void commandStarting() {
		}

	}
}
