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
package au.gov.naa.digipres.spyd.core;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.core.server.SpydServer;
import au.gov.naa.digipres.spyd.dao.ConnectionException;
import au.gov.naa.digipres.spyd.dao.DataAccessManager;
import au.gov.naa.digipres.spyd.dao.hibernate.HibernateDataAccessManager;
import au.gov.naa.digipres.spyd.plugin.PluginManager;
import au.gov.naa.digipres.spyd.preferences.SpydPreferences;

public class Spyd {

	private DataAccessManager dataAccessManager = null;

	private Logger logger;

	private SpydPreferences preferences;
	private PluginManager pluginManager;

	private SpydServer server;

	public Spyd() {
		this(true);
	}

	public Spyd(boolean debug) {

		pluginManager = new PluginManager(this);
		preferences = pluginManager.getPreferenceManager().getPreferences();
		logger = pluginManager.getCommunicationManager().getClassLogger(this);
	}

	/**
	 * Set the data access manager to use. If the data access manager is NOT null, (ie it
	 * has already been set) then throw an illegal state exception.
	 * 
	 * @param dataAccessManager
	 */
	public void setDataAccessManager(DataAccessManager dataAccessManager) {
		// This may need to be reconsidered in future.
		if (this.dataAccessManager != null) {
			throw new IllegalStateException("Can not reset data access manager!");
		}
		this.dataAccessManager = dataAccessManager;
	}

	/**
	 * Return the data access manager. If the data access manager has not been instantiated, then we
	 * create an instance of the default data access manager (the hibernate one!)
	 * @return The data access manager
	 */
	public DataAccessManager getDataAccessManager() {
		if (dataAccessManager == null) {
			initDataAccessManager();
		}
		return dataAccessManager;
	}

	/**
	 * Initialise the data access manager to the default. This is currently the
	 * {@link HibernateDataAccessManager}
	 * 
	 */
	private void initDataAccessManager() {
		dataAccessManager = new HibernateDataAccessManager(this);
	}

	/**
	 * Return the connection properties for the current data access manager.
	 * @see DataAccessManager#getConnectionProperties()
	 */
	public Map<String, String> getConnectionProperties() {
		return getDataAccessManager().getConnectionProperties();
	}

	/**
	 * Connect to the data store - this call is passed to the connection exception.
	 * @see DataAccessManager#connectToDataStore(Map)
	 * @param connectionProperties
	 * @throws ConnectionException in the case of an error
	 */
	public void connectToDataStore(Map<String, String> connectionProperties) throws ConnectionException {
		getDataAccessManager().connectToDataStore(connectionProperties);
	}

	/**
	 * Disconnect from the data store.
	 * @throws ConnectionException
	 */
	public void disconnectFromDataStore() throws ConnectionException {
		getDataAccessManager().disconnect();
	}

	/**
	 * Returns the plugin manager.
	 * @return
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public SpydPreferences getPreferences() {
		return preferences;
	}

	public void startNetworkService() throws IOException, SQLException {
		if (server == null) {
			server = new SpydServer(getPreferences(), this);
			server.start();
			logger.fine("Starting server");
		} else {
			logger.warning("Failed to start server, server already running");
		}

	}

	public void stopNetworkService() {
		if (server != null) {
			server.shutdownServer();
			logger.fine("Shutting down server");
		}
	}

	/**
	 * Load a single plugin by name. The plugin should exist on
	 * the class path. If the plugin is unable to found, then
	 * a SpydException may be thrown.
	 * <p>
	 * This is often the preferred way of loading plugins,
	 * since if a third party application is asking Spyd to
	 * load a number of plugins and for any reason can not load
	 * one, this will allow the calling application to know
	 * exactly which plugin has failed to load. However, since
	 * plugins have dependencies, it is often easier to simply
	 * use the method loadPlugins(List&#60String&#62 pluginList)</p>
	 * 
	 *
	 * @param pluginName The name of the plugin
	 * @throws SpydException
	 * @see #loadPlugins(List)
	 */
	public void loadPlugin(String pluginName) throws SpydException {
		List<String> pluginNameList = new ArrayList<String>();
		pluginNameList.add(pluginName);
		pluginManager.loadPlugins(pluginNameList);
	}

	/**
	 * Load a number of plugins by name. The plugins should already be on the
	 * class path.
	 * <p>
	 * This method should be used when a number of plugins are to be loaded through
	 * Spyd, especially when some of these plugins have dependencies. Using this
	 * method, the plugin manager will actually load the plugins in the correct
	 * order to ensure that any dependencies are correctly handled.
	 * </p><p>
	 * Limitations: When loading plugins with this method there is a potential
	 * problem that if there is a major error loading a plugin, then it may be 
	 * difficult to work out which plugins were loaded.
	 * </p>
	 * @param pluginList The String names of the plugins to be loaded
	 * @throws SpydException If there is an exception while loading plugins.
	 */
	public void loadPlugins(List<String> pluginList) throws SpydException {
		pluginManager.loadPlugins(pluginList);
	}

	/**
	 * Load plugins from a file object. The file object is either jar file or a
	 * folder containing one or more jar files.
	 * 
	 * @param pluginLocation
	 * @throws SpydException In case of plugin being unable to be loaded for some reason
	 * @throws IOExcetpion In case of plugin being unable to be loaded for some reason
	 */
	public void loadPlugins(File pluginLocation) throws SpydException, IOException {
		pluginManager.loadPlugins(pluginLocation);
	}

}
