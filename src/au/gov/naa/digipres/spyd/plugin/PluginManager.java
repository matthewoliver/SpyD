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
package au.gov.naa.digipres.spyd.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandManager;
import au.gov.naa.digipres.spyd.core.DeSerializeClassLoader;
import au.gov.naa.digipres.spyd.core.Spyd;
import au.gov.naa.digipres.spyd.core.SpydException;
import au.gov.naa.digipres.spyd.module.ModuleManager;
import au.gov.naa.digipres.spyd.module.SpydModule;

/**
 * This class is responsible for managing the loading of plugins in to Spyd.
 */
public class PluginManager {

	private Spyd spyd;

	/*
	 * These are the component managers
	 */
	private CommandManager commandManager;
	private ModuleManager moduleManager;

	/**
	 * The deserialised class loader
	 */
	private DeSerializeClassLoader deserClassLoader = new DeSerializeClassLoader(getClass().getClassLoader());

	/**
	 * A list of all the names of the plugins that have been loaded already.
	 */
	private List<SpydPlugin> loadedPlugins = new ArrayList<SpydPlugin>();

	/**
	 * A list of all the names of the plugins that could not be loaded.
	 */
	private List<SpydPlugin> unloadablePlugins = new ArrayList<SpydPlugin>();

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Plugin manager main constructor.
	 * Initialise all the component managers required for Spyd to run.
	 * 
	 * Provide each of the component managers a reference to the pluginManager
	 * so that they can talk to each other using the pluginManager as a conduit.
	 * ie the ModuleManager can talk to the commandManager by calling: pluginManager.getcommandManager()
	 *
	 * Also initialise the loadManagers list which is used when loading a plugin.
	 */
	public PluginManager(Spyd spyd) {
		this.spyd = spyd;

		// Each of the different types of classes is loaded and managed by a
		// Manager class. Here we enumerate all the Managers.
		commandManager = new CommandManager(this);
		moduleManager = new ModuleManager(this);
	}

	/**
	 * Get the Spyd object
	 * @return
	 */
	public Spyd getSpyd() {
		return spyd;
	}

	/**
	 * Given a Spyd plugin jar file, we extract the "name.properties" file from the
	 * top level, which tells us the official name of this plugin which
	 * corresponds to the package name with slash separators. A typical name
	 * would be au/gov/naa/digipres/spyd/plugin/<plugin name>/<class name>
	 * 
	 * @param pluginFile
	 *            The Spyd Plugin Jar File
	 * @return String The internal name of the plugin
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static String getPluginClassName(File pluginFile) throws IOException, MalformedURLException {
		URL url = pluginFile.toURI().toURL();
		// we don't use deserClassLoader here because it would
		// have conflicting name.properties
		URLClassLoader cl = new URLClassLoader(new URL[] {url}, null);
		InputStream is = cl.getResourceAsStream("name.properties");
		if (is == null) {
			throw new IOException("Cannot find name.properties in plugin " + pluginFile);
		}
		Properties namep = new Properties();
		namep.load(is);
		String name = namep.getProperty("classname");
		return name;
	}

	/**
	 * This method allows xena to load a plugin by providing a file object for the location of the Xena
	 * plugin or plugins. This file may be a single Jar file, or it may be a directory of Jar files.
	 * <p>
	 * If the plugin location is a directory, the contents will be listed and any Jars will be assumed to
	 * be plugins. This is a common case when you wish to have a 'plugins' directory for an application
	 * that uses Xena.</p>
	 * <p>
	 * If the plugin location is a file, then we check to ensure it is a valid jar file and then load it.
	 * In terms of actually loading, what we really do is the following:
	 * <ol>
	 * <li> Add the jars to the class path, so we can load the classes therein,</li>
	 * <li> Find the name.properties file, get the name of the plugin
	 *      and add it to a list (aptly called pluginNames)</li>
	 * <li> Load all the plugins be name (which is now possible since all the Jars have
	 * been added to the classpath).</li>
	 * </lo></p>
	 * 
	 * @param pluginLocation File that is either a specific plugin Jar File or a directory
	 *      containing plugin jar files.
	 * @throws IOException
	 * @throws XenaException
	 */
	public void loadPlugins(File pluginLocation) throws IOException, SpydException {
		// create a list of plugin files and plugin names...
		ArrayList<File> pluginFiles = new ArrayList<File>();

		if (pluginLocation.isDirectory()) {
			// handle directory -> add any jar files to our file list.
			File[] list = pluginLocation.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".jar");
				}
			});
			for (File element : list) {
				pluginFiles.add(element);
			}
		} else if (pluginLocation.isFile()) {
			// handle single file -> if its a jar file, add it to our file list.
			String theFilename = pluginLocation.getName();
			String extension = "";
			int whereDot = theFilename.lastIndexOf('.');
			if (0 < whereDot && whereDot <= theFilename.length() - 2) {
				extension = theFilename.substring(whereDot + 1);
			}
			if ("jar".equals(extension)) {
				pluginFiles.add(pluginLocation);
			}
		}

		// go through our plugin files list and try to add them to the class path,
		// and get the name from the name.properties file and add it to our plugin names list.
		for (File pluginFile : pluginFiles) {
			String pluginClassName = getPluginClassName(pluginFile);
			try {
				deserClassLoader.addURL(pluginFile.toURI().toURL());

				Class<?> pluginClass = Class.forName(pluginClassName, true, deserClassLoader);
				SpydPlugin spydPlugin = (SpydPlugin) pluginClass.newInstance();
				loadPlugin(spydPlugin);
			} catch (MalformedURLException malformedURLException) {
				// hmmm.... is this useful or necessary?
				// TODO - handle this better.
				malformedURLException.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new SpydException("Problem loading plugin", e);
			} catch (InstantiationException e) {
				throw new SpydException("Problem loading plugin", e);
			} catch (IllegalAccessException e) {
				throw new SpydException("Problem loading plugin", e);
			}
		}
	}

	public void loadPlugins(List<String> classNameList) throws SpydException {
		for (String className : classNameList) {
			try {
				SpydPlugin spydPlugin = (SpydPlugin) Class.forName(className).newInstance();
				loadPlugin(spydPlugin);
			} catch (ClassNotFoundException e) {
				throw new SpydException("Problem loading plugin", e);
			} catch (InstantiationException e) {
				throw new SpydException("Problem loading plugin", e);
			} catch (IllegalAccessException e) {
				throw new SpydException("Problem loading plugin", e);
			}
		}
	}

	/**
	 * @param spydPlugin
	 * @throws XenaException 
	 */
	private void loadPlugin(SpydPlugin spydPlugin) throws SpydException {
		// Modules
		List<SpydModule> moduleList = spydPlugin.getModules();
		if (moduleList != null && !moduleList.isEmpty()) {
			moduleManager.addModules(moduleList);
		}

		// Commands
		List<Command> commandList = spydPlugin.getCommands();
		if (commandList != null && !commandList.isEmpty()) {
			commandManager.addCommands(commandList);
		}

		loadedPlugins.add(spydPlugin);
		logger.fine("Successfully loaded the " + spydPlugin.getName() + " (" + spydPlugin.getVersion() + ") plugin");
	}

	/**
	 * Return the string representation of the plugin manager. At this stage the
	 * plugin manager string representation is simply.... "Plugin manager!"
	 * followed by a list of loaded plugins.
	 * @return The string representation of the plugin manager.
	 */
	@Override
	public String toString() {
		StringBuffer returnBuffer = new StringBuffer("Plugin manager!");
		returnBuffer.append(System.getProperty("line.separator"));
		returnBuffer.append("Loaded plugins:");
		returnBuffer.append(System.getProperty("line.separator"));
		for (Object element : getLoadedPlugins()) {
			returnBuffer.append(element);
			returnBuffer.append(System.getProperty("line.separator"));
		}
		return new String(returnBuffer);
	}

	// *******************************
	// * *
	// * GETTERS AND SETTERS FOLLOW *
	// * *
	// *******************************

	/**
	 * Return the list of loaded plugins.
	 * @return Returns the list of loaded plugins.
	 */
	public List<SpydPlugin> getLoadedPlugins() {
		return loadedPlugins;
	}

	/**
	 * @return Returns the unloadablePlugins.
	 */
	public List<SpydPlugin> getUnloadablePlugins() {
		return unloadablePlugins;
	}

	/**
	 * @return Returns the deserClassLoader.
	 */
	public DeSerializeClassLoader getDeserClassLoader() {
		return deserClassLoader;
	}

	/**
	 * @return Returns the commandManager.
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * @return Returns the moduleManager.
	 */
	public ModuleManager getModuleManager() {
		return moduleManager;
	}
}
