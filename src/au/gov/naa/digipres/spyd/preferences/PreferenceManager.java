package au.gov.naa.digipres.spyd.preferences;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import au.gov.naa.digipres.spyd.core.SpydException;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class PreferenceManager {

	private PluginManager pluginManager;
	private List<String> pluginPreferences;
	private SpydPreferences preferences;

	public PreferenceManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		pluginPreferences = new Vector<String>();
		preferences = SpydPreferences.createPreferences();
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public List<String> getPluginPreferences() {
		return pluginPreferences;
	}

	public void addPreference(String pref) throws SpydException {
		if (pluginPreferences.contains(pref)) {
			throw new SpydException("Preference '" + pref + "' already loaded!");
		}

		pluginPreferences.add(pref);
	}

	public void addPreferences(List<String> preferences) throws SpydException {
		for (String pref : preferences) {
			addPreference(pref);
		}
	}

	public void updateFromFile(String propertiesFile) throws IOException {
		preferences.updateFromFile(propertiesFile);
	}

	public void updateFromFile(File propertiesFile) throws IOException {
		updateFromFile(propertiesFile.getAbsolutePath());
	}

	public SpydPreferences getPreferences() {
		return preferences;
	}

}
