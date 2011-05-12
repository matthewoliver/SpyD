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
package au.gov.naa.digipres.spyd.dao;

import java.util.Map;

import au.gov.naa.digipres.spyd.core.Spyd;

/**
 * <p>The data access manager class is an abstract class that encapsulates the Data Access Objects. This
 * class is also responsible for keeping the state of the current user and data store connection current.</p>
 * 
 * <p>This class acts as a broker to the DAO objects, classes requiring access to the DAOs should user the getter
 * methods in this class to get DAO objects; this class will ensure that they are only passed to appropriate objects.</p>
 * 
 * <p>As a rule, as much implementation should be done in here as possible; concrete implementations of this class
 * should only have to do the following:
 * <ul>
 * <li>Implement the connection specific methods: {@link #getConnectionProperties()}, {@link #connectToDataStore(Map)}</li>
 * <li>Implement getters for the concrete DAO objects: {@link #getUserDAO()}, {@link #getTransferJobDAO()}, etc.</li>
 * <li></li>
 * </ul>
 * </p>
 * 
 * <p></p>
 * 
 * @author andy
 *
 */
public abstract class DataAccessManager {

	protected Spyd spyd;
	protected boolean connected;

	/**
	 */
	public DataAccessManager(Spyd spyd) {
		this.spyd = spyd;
	}

	/**
	 * Get the connection properties for a concrete instance of a Data Access Manager. These
	 * detail the things that are required to make the connection.
	 * @return
	 */
	public abstract Map<String, String> getConnectionProperties();

	/**
	 * Connect to the data store for a concrete instance of a data access manager. It requires a map of
	 * connection properties that are required to make the connection.
	 * @param connectionProperties
	 * @throws ConnectionException
	 */
	public abstract void connectToDataStore(Map<String, String> connectionProperties) throws ConnectionException;

	/**
	 * Completely disconnect from the data store.
	 * @throws ConnectionException
	 */
	public abstract void disconnect() throws ConnectionException;

	/**
	 * Simple method to validate the connection before returning a task;
	 * check we are connected, logged on, and the task is not null.
	 * @param task
	 */
	private void validateConnection() {
		if (!isConnected()) {
			throw new IllegalStateException("Not connected!");
		}
	}

	/**
	 * Get an instance of the ItemRecordDAO.
	 * @return
	 */
	public abstract ItemRecordDAO getItemRecordDAO();

	/**
	 * Return a boolean based on whether the data access manager is actually connected to the data store.
	 */
	public final boolean isConnected() {
		return connected;
	}

	/**
	 * Begin a transaction context. This is to be handled by the concrete data access managers.
	 * Some will do nothing, others may need this for scalability.
	 */
	public abstract void beginTransaction();

	/**
	 * Commit a transaction. This is to be handled by the concrete data access managers.
	 * Some will do nothing, others may need this for scalability.
	 */
	public abstract void commitTransaction();

	/**
	 * Rollback a transaction. This is to be handled by the concrete data access managers.
	 * Some will do nothing, others may need this for scalability.
	 */
	public abstract void rollbackTransaction();

}
