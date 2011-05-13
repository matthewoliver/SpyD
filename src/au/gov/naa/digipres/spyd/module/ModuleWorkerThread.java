/**
* This file is part of Spyd.
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
package au.gov.naa.digipres.spyd.module;

import java.util.List;

import au.gov.naa.digipres.spyd.core.server.SpydServer;

public class ModuleWorkerThread extends Thread {

	private ModuleManager moduleManager;
	private boolean workerRunning = true;
	private SpydModule module;

	public ModuleWorkerThread(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	/**
	 * Completely shut down this worker.
	 */
	synchronized void stopWorker() {
		workerRunning = false;
		notify();
	}

	public void setModule(SpydModule module) {
		this.module = module;
	}

	public SpydModule getModule() {
		return module;
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
			if (module == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					return;
				}
			}

			// If the thread has been closed, do nothing further
			if (workerRunning) {
				try {
					handleModule();
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Go back in wait queue if there's fewer than numHandler connections.
				module = null;
				List<ModuleWorkerThread> workerPool = moduleManager.getWorkerPool();
				synchronized (workerPool) {
					if (workerPool.size() >= SpydServer.threadPoolSize) {
						// Too many threads, exit this one
						return;
					}
					workerPool.add(this);
				}
			}
		}
	}

	private synchronized void handleModule() {

	}

}
