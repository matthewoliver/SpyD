package au.gov.naa.digipres.spyd.preferences;

import java.util.List;
import java.util.Vector;

import au.gov.naa.digipres.spyd.core.SpydException;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class PreferenceManager {

	private PluginManager pluginManager;
	private List<String> preferences;

	public PreferenceManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		preferences = new Vector<String>();
	}

	public List<String> getPreferences() {
		return preferences;
	}

	public void addPreference(String pref) throws SpydException {
		if (preferences.contains(pref)) {
			throw new SpydException("Preference '" + pref + "' already loaded!");
		}

		preferences.add(pref);
	}

	public void addPreferences(List<String> preferences) throws SpydException {
		for (String pref : preferences) {
			addPreference(pref);
		}
	}

}
