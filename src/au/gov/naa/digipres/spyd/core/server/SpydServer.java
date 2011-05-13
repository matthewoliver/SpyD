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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.command.CommandManager;
import au.gov.naa.digipres.spyd.core.Constants;
import au.gov.naa.digipres.spyd.core.Spyd;
import au.gov.naa.digipres.spyd.core.SpydPreferences;

public class SpydServer extends Thread {
	public static final int SERVER_PORT = 3444;
	private static final int MAX_PORTS_TO_ATTEMPT = 50;
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int DEFAULT_THREAD_POOL_SIZE = 5;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private Spyd spyd;
	private SpydPreferences preferences;
	private CommandManager commandManager;

	private boolean running = true;

	// Where worker threads stand idle
	private Vector<SpydServerWorker> threads;

	// initial number of worker threads. More workers will be added if there are none available for a request.
	public static int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

	private ServerSocket serverSocket;
	private int port = SERVER_PORT;
	private int timeout = SOCKET_TIMEOUT;

	/**
	 * Initialises the server (including logging, the email sender and the checksum checker) and then begin listening
	 * for incoming client connections.
	 * 
	 * @param configProperties
	 * @throws IOException
	 * @throws SQLException
	 */
	public SpydServer(SpydPreferences preferences, Spyd spyd) throws IOException, SQLException {
		super("Spyd Server (primary)");

		this.preferences = preferences;

		if (preferences.getPreference(SpydPreferences.LISTENING_PORT).length() > 0) {
			try {
				this.port = Integer.parseInt(preferences.getPreference(SpydPreferences.LISTENING_PORT));
			} catch (NumberFormatException ex) {
				logger.warning("Error setting listening port to '" + preferences.getPreference(SpydPreferences.LISTENING_PORT)
				               + "' attempting default port '" + SERVER_PORT + "'");
				this.port = SERVER_PORT;
			}
		}

		if (preferences.getPreference(SpydPreferences.THREAD_POOL_SIZE).length() > 0) {
			try {
				this.threadPoolSize = Integer.parseInt(preferences.getPreference(SpydPreferences.THREAD_POOL_SIZE));
			} catch (NumberFormatException ex) {
				logger.warning("Error setting the thread pool size to '" + preferences.getPreference(SpydPreferences.THREAD_POOL_SIZE)
				               + "' using default size of '" + DEFAULT_THREAD_POOL_SIZE + "'");
				this.threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
			}
		}

		this.spyd = spyd;
		setCommandManager(spyd.getPluginManager().getCommandManager());

		initLogging();
		//		startChecker(configProperties);
		initServer();

		runServer();
	}

	/**
	 * Creates the server socket, and starts the worker threads.
	 * @throws IOException
	 */
	private void initServer() throws IOException {
		// Initialise the server socket. If the port is already in use, try more ports until we find one that is free.
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException iex) {
			// Assume that this failed because the port was in use
			int origPort = port;
			boolean portFound = false;
			for (int newPort = port + 1; newPort <= port + MAX_PORTS_TO_ATTEMPT; newPort++) {
				try {
					serverSocket = new ServerSocket(newPort);

					// If this succeeds, set the port to newPort and flag that we have found a socket
					port = newPort;
					portFound = true;
					break;
				} catch (IOException newiex) {
					// Do nothing, just start the loop again to try another port
				}
			}

			// If we have not found a free port to use, give up and throw an exception
			if (!portFound) {
				throw new IOException("Could not find a port to use for the spyd server - tried ports " + origPort + " to "
				                      + (origPort + MAX_PORTS_TO_ATTEMPT));
			}
		}

		// Start worker threads. They will remain in a wait state until notified by the setSocket method.
		threads = new Vector<SpydServerWorker>();
		for (int i = 0; i < threadPoolSize; ++i) {
			SpydServerWorker w = new SpydServerWorker(this);
			new Thread(w, "Spyd worker #" + i).start();
			threads.addElement(w);
		}
	}

	private void initLogging() {
		// Main logger object
		logger = Logger.getLogger(Constants.ROOT_LOGGING_PACKAGE);
		logger.setLevel(Level.ALL);

		logger.finest("Logging initialised");
	}

	/**
	 * Listens for incoming connections, and passes any new connection to one of the threads in the worker pool.
	 * 
	 * The server is shut down if "running" is set to false by another method, in which case the server socket is closed
	 * and the workers are stopped.
	 */
	private void runServer() {

		System.out.println("Spyd server started on port " + port);

		try {
			// This loop is only ended when the shutdownServer method is called from an external source.
			while (running) {

				// The socket will listen for a connection for this period of time. 
				serverSocket.setSoTimeout(timeout);

				try {
					// Listen for a connection. If no connection is made,
					// a SocketTimeoutException is thrown and the loop will be restarted.
					Socket clientSocket = serverSocket.accept();

					SpydServerWorker w = null;
					synchronized (threads) {
						// threads will be empty if all the initial workers are busy
						if (threads.isEmpty()) {
							// Create a new worker and start it.
							SpydServerWorker ws = new SpydServerWorker(this);
							ws.setSocket(clientSocket);
							new Thread(ws, "additional worker").start();
						} else {
							// Get the first worker, and activate it by setting the socket
							w = threads.elementAt(0);
							threads.removeElementAt(0);
							w.setSocket(clientSocket);
						}
					}
				} catch (SocketTimeoutException stex) {
					// Do nothing, just try again
				}
			}

			// If we have reached this point then the server is shutting down.
			logger.log(Level.FINE, "Shutting down server.");
			serverSocket.close();
			stopWorkers();

		} catch (Exception ex) {
			// An unexpected error occurred - log the error and shut down the server
			logger.log(Level.SEVERE, "An unexpected error occurred when initialising the web server. Shutting down server.", ex);
			stopWorkers();
		}
	}

	/**
	 * Shut down the web server. This simply sets the running field to
	 * false, which will cause the main thread loop to end, and the worker
	 * threads to be stopped.
	 */
	public void shutdownServer() {
		running = false;
	}

	/**
	 * Stops any active WebServerWorker threads.
	 */
	private void stopWorkers() {
		for (SpydServerWorker workerThread : threads) {
			workerThread.stopWorker();
		}

		// Clear out threads for the next run
		threads.clear();
	}

	public Vector<SpydServerWorker> getWorkerPool() {
		return threads;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

}
