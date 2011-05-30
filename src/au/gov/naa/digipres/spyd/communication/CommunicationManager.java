package au.gov.naa.digipres.spyd.communication;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.core.Constants;
import au.gov.naa.digipres.spyd.core.SpydPreferences;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class CommunicationManager {
	private PluginManager pluginManager;
	private Logger logger;
	private Logger rootLogger;

	private FileHandler logFileHandler;
	private SpydPreferences preferences;

	public CommunicationManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		preferences = pluginManager.getPreferenceManager().getPreferences();
		// setup the root logger. 
		setupRootLogger();
	}

	private void setupRootLogger() {
		// Main logger object
		rootLogger = Logger.getLogger(Constants.ROOT_LOGGING_PACKAGE);

		// The default top-level logger has a console handler set to only show INFO level messages - don't use this
		rootLogger.setUseParentHandlers(false);

		// Add FileHandler
		if (logFileHandler != null) {
			rootLogger.removeHandler(logFileHandler);
			logFileHandler.flush();
			logFileHandler.close();
		}

		try {
			String logFilePattern = Constants.DEFAULT_LOG_FILE_PATTERN;

			//get the log file directory

			File logFileDir = new File(Constants.DEFAULT_LOG_FILE_DIR);
			if (!logFileDir.exists() && !logFileDir.mkdirs()) {
				throw new IllegalStateException("Log file directory could not be created!");
			}
			logFileHandler = new FileHandler(logFilePattern, 1000000, 2, true);
			logFileHandler.setFormatter(Constants.DEFAULT_LOG_FORMATTER);
			rootLogger.addHandler(logFileHandler);
		} catch (Exception e) {
			logger.log(Level.FINER, "Could not start logging File Handler", e);
		}

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
