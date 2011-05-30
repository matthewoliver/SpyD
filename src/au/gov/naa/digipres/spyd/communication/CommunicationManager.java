package au.gov.naa.digipres.spyd.communication;

import java.util.logging.Level;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class CommunicationManager {
	private PluginManager pluginManager;
	private Logger logger;

	public CommunicationManager(PluginManager pluginManager, Logger logger) {
		this.pluginManager = pluginManager;

	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	// Logging control.
	public void logFinest(String message) {
		log(Level.FINEST, message);
	}

	public void logFiner(String message) {
		log(Level.FINER, message);
	}

	public void logFine(String message) {
		log(Level.FINE, message);
	}

	public void logAll(String message) {
		log(Level.ALL, message);
	}

	public void logInfo(String message) {
		log(Level.INFO, message);
	}

	public void logWarning(String message) {
		log(Level.WARNING, message);
	}

	public void logSevere(String message) {
		log(Level.SEVERE, message);
	}

	public void log(Level logLevel, String message) {
		logger.log(logLevel, message);
	}
}
