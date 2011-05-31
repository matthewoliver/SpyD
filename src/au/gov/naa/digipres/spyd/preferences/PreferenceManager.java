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

	private List<PreferencesListener> preferencesListeners;

	public PreferenceManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		pluginPreferences = new Vector<String>();
		preferences = SpydPreferences.createPreferences(this);
		preferencesListeners = new Vector<PreferencesListener>();
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
		firePreferencesUpdatedEvent();
	}

	public void addPreferences(List<String> preferences) throws SpydException {
		for (String pref : preferences) {
			addPreference(pref);
		}
	}

	public void updateFromFile(String propertiesFile) throws IOException {
		preferences.updateFromFile(propertiesFile);
		firePreferencesUpdatedEvent();
	}

	public void updateFromFile(File propertiesFile) throws IOException {
		updateFromFile(propertiesFile.getAbsolutePath());
	}

	public synchronized SpydPreferences getPreferences() {
		return preferences;
	}

	public void registerPreferencesListener(PreferencesListener listener) {
		if (!preferencesListeners.contains(listener)) {
			preferencesListeners.add(listener);
		}
	}

	public void removePreferencesListener(PreferencesListener listener) {
		if (preferencesListeners.contains(listener)) {
			preferencesListeners.remove(listener);
		}
	}

	void firePreferencesUpdatedEvent() {
		for (PreferencesListener listener : preferencesListeners) {
			listener.preferencesUpdated();
		}
	}
}
