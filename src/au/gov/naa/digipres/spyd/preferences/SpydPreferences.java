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
package au.gov.naa.digipres.spyd.preferences;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

import au.gov.naa.digipres.spyd.dao.hibernate.HibernateDataAccessManager;

/**
 * A simple class to encapsulate the preferences for Spyd.
 */
public class SpydPreferences {

	public static final String DATABASE_NAME = "db.databasename";
	public static final String DATABASE_HOST = "db.hostname";
	public static final String DATABASE_USER = "db.username";
	public static final String DATABASE_PORT = "db.port";

	public static final String DATABASE_USERNAME = "db.username";
	public static final String DATABASE_PASSWORD = "db.password";

	public static final String LISTENING_PORT = "listening.port";

	public static final String THREAD_POOL_SIZE = "thread.pool.size";

	public static final String AUTO_LOAD_MODULES = "auto.load.modules";

	// SMTP Settings
	public static final String EMAIL_ADDRESS = "email.address";
	public static final String SMTP_PORT = "smpt.port";
	public static final String SMTP_SERVER = "smpt.server";

	//Logging
	public static final String LOGGING_DIRECTORY = "log.directory";
	public static final String LOGGING_LEVEL = "log.level";

	protected Map<String, PreferenceValue> loadedPreferences = new HashMap<String, PreferenceValue>();
	protected PreferenceManager preferenceManager = null;

	private static SpydPreferences preferences;

	private SpydPreferences(PreferenceManager preferenceManager) {
		this.preferenceManager = preferenceManager;
	}

	/**
	 * Save any preferences that have been changed to the store.
	 */
	public void savePreferences() {

		Preferences p = Preferences.userNodeForPackage(this.getClass());

		for (String key : loadedPreferences.keySet()) {
			PreferenceValue value = loadedPreferences.get(key);

			if (value != null && value.isDirty() && value.getValue() != null) {
				p.put(key, value.getValue());
			}
		}
	}

	/**
	 * Retrieve the preference with the specified key.
	 * @param key
	 * @return
	 */
	public String getPreference(String key) {
		PreferenceValue value = loadedPreferences.get(key);

		if (value == null) {
			String loadedValue = Preferences.userNodeForPackage(this.getClass()).get(key, null);

			value = new PreferenceValue(loadedValue);
			loadedPreferences.put(key, value);
		}

		return value.getValue();
	}

	/**
	 * Updates the Spyd settings based on the settings file sent in.
	 * @param propertiesPath The path to settings file.
	 * @throws IOException
	 */
	public void updateFromFile(String propertiesPath) throws IOException {
		Properties newSettings = new Properties();
		newSettings.load(ClassLoader.getSystemResourceAsStream(propertiesPath));

		if (newSettings.containsKey(DATABASE_HOST)) {
			setPreference(DATABASE_HOST, newSettings.getProperty(DATABASE_HOST));
		}
		if (newSettings.containsKey(DATABASE_NAME)) {
			setPreference(DATABASE_NAME, newSettings.getProperty(DATABASE_NAME));
		}
		if (newSettings.containsKey(DATABASE_USER)) {
			setPreference(DATABASE_USER, newSettings.getProperty(DATABASE_USER));
		}
		if (newSettings.containsKey(DATABASE_PORT)) {
			setPreference(DATABASE_PORT, newSettings.getProperty(DATABASE_PORT));
		}
		if (newSettings.containsKey(DATABASE_USERNAME)) {
			setPreference(DATABASE_USERNAME, newSettings.getProperty(DATABASE_USERNAME));
		}
		if (newSettings.containsKey(DATABASE_PASSWORD)) {
			setPreference(DATABASE_PASSWORD, newSettings.getProperty(DATABASE_PASSWORD));
		}

		if (newSettings.containsKey(LISTENING_PORT)) {
			setPreference(LISTENING_PORT, newSettings.getProperty(LISTENING_PORT));
		}
		if (newSettings.containsKey(THREAD_POOL_SIZE)) {
			setPreference(THREAD_POOL_SIZE, newSettings.getProperty(THREAD_POOL_SIZE));
		}
		if (newSettings.containsKey(AUTO_LOAD_MODULES)) {
			setPreference(AUTO_LOAD_MODULES, newSettings.getProperty(AUTO_LOAD_MODULES));
		}
		if (newSettings.containsKey(EMAIL_ADDRESS)) {
			setPreference(EMAIL_ADDRESS, newSettings.getProperty(EMAIL_ADDRESS));
		}
		if (newSettings.containsKey(SMTP_SERVER)) {
			setPreference(SMTP_SERVER, newSettings.getProperty(SMTP_SERVER));
		}
		if (newSettings.containsKey(SMTP_PORT)) {
			setPreference(SMTP_PORT, newSettings.getProperty(SMTP_PORT));
		}
		if (newSettings.containsKey(LOGGING_DIRECTORY)) {
			setPreference(LOGGING_DIRECTORY, newSettings.getProperty(LOGGING_DIRECTORY));
		}
		if (newSettings.containsKey(LOGGING_LEVEL)) {
			setPreference(LOGGING_LEVEL, newSettings.getProperty(LOGGING_LEVEL));
		}

		// update preferences loaded from the preferences manager (from other plugins).
		for (String pref : preferenceManager.getPluginPreferences()) {
			if (newSettings.containsKey(pref)) {
				setPreference(pref, newSettings.getProperty(pref));
			}
		}
	}

	/**
	 * Get the database connections settings in the form required by the NMC data access managers.
	 * @return The database settings
	 * @throws IOException 
	 */
	public Map<String, String> getDBConnectionProperties() throws IOException {
		Map<String, String> dbConnectionProperties = new HashMap<String, String>();
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_NAME, getPreference(DATABASE_NAME));
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_HOSTNAME, getPreference(DATABASE_HOST));
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_PORT, getPreference(DATABASE_PORT));
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_USERNAME, getPreference(DATABASE_USERNAME));
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_PASSWORD, getPreference(DATABASE_PASSWORD));

		//TODO: load these from a properties file or something
		Properties dbProperties = new Properties();
		dbProperties.load(ClassLoader.getSystemResourceAsStream("etc/db.properties"));
		String vendor = dbProperties.getProperty("vendor"); //$NON-NLS-1$

		String driverName = dbProperties.getProperty(vendor + ".driver"); //$NON-NLS-1$
		String dialectName = dbProperties.getProperty(vendor + ".dialect");
		String connectionString = dbProperties.getProperty(vendor + ".connect"); //$NON-NLS-1$

		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_DRIVER_NAME, driverName);
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_DIALECT, dialectName);
		dbConnectionProperties.put(HibernateDataAccessManager.DATABASE_CONNECTION_STRING, connectionString);
		dbConnectionProperties.put(HibernateDataAccessManager.HIBERNATE_CONFIGURATION_FILE, "etc/hibernate.cfg.xml");

		return dbConnectionProperties;
	}

	/**
	 * Update the value of the preference with the specified key.
	 * @param key
	 * @param value
	 */
	public void setPreference(String key, String value) {
		PreferenceValue preferenceValue = loadedPreferences.get(key);

		if (preferenceValue == null) {
			preferenceValue = new PreferenceValue(value);
			loadedPreferences.put(key, preferenceValue);
		} else {
			preferenceValue.setValue(value);
		}

		preferenceValue.setDirty(true);
	}

	private class PreferenceValue {
		private boolean dirty = false;
		private String value;

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public PreferenceValue(String value) {
			this.value = value;
		}

	}

	public PreferenceManager getPreferenceManager() {
		return preferenceManager;
	}

	static SpydPreferences createPreferences(PreferenceManager preferenceManager) {
		if (preferences == null) {
			preferences = new SpydPreferences(preferenceManager);
		}

		return preferences;
	}
}
