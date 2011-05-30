package au.gov.naa.digipres.spyd.communication;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.core.Constants;
import au.gov.naa.digipres.spyd.plugin.PluginManager;
import au.gov.naa.digipres.spyd.preferences.PreferenceManager;
import au.gov.naa.digipres.spyd.preferences.SpydPreferences;

public class CommunicationManager {
	private PluginManager pluginManager;
	private Logger logger = getClassLogger(this);
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
			PreferenceManager preferenceManager = getPluginManager().getPreferenceManager();
			String logDirectory = preferenceManager.getPreferences().getPreference(SpydPreferences.LOGGING_DIRECTORY);

			String loggingLevel = preferenceManager.getPreferences().getPreference(SpydPreferences.LOGGING_LEVEL);
			if (loggingLevel == null) {
				loggingLevel = "INFO";
			}

			if (logDirectory == null) {
				logDirectory = Constants.DEFAULT_LOG_FILE_DIR;
			}

			File logFileDir = new File(logDirectory);
			if (!logFileDir.exists() && !logFileDir.mkdirs()) {
				throw new IllegalStateException("Log file directory could not be created!");
			}
			logFileHandler = new FileHandler(logFilePattern, Constants.DEFAULT_LOG_FILE_LIMIT, 2, true);
			logFileHandler.setFormatter(Constants.DEFAULT_LOG_FORMATTER);
			rootLogger.addHandler(logFileHandler);
			rootLogger.setLevel(Level.parse(loggingLevel));
		} catch (Exception e) {
			logger.log(Level.FINER, "Could not start logging File Handler", e);
		}

	}

	private void addLogFileHandler(Logger logger, String logFileIdentifier) {
		try {
			FileHandler tmpLogFileHandler =
			    new FileHandler(logFileIdentifier + Constants.DEFAULT_LOG_FILE_PATTERN_ENDING, Constants.DEFAULT_LOG_FILE_LIMIT, 2, true);
			tmpLogFileHandler.setFormatter(Constants.DEFAULT_LOG_FORMATTER);
			logger.addHandler(tmpLogFileHandler);
		} catch (Exception e) {
			this.logger.log(Level.FINER, "Could not start logging File Handler", e);
		}
	}

	/***
	 * Return a logger with a filehandler inherited by the parent logger of the Object classpath.
	 * @param objectClass The object whose class name we use.
	 * @return A logger object
	 */
	public Logger getClassLogger(Object objectClass) {
		return Logger.getLogger(objectClass.getClass().getName());
	}

	/***
	 * Return a logger that outputs to a new log file <log directory>logFileIdentifier<num>.log. 
	 * Each class under the objectClass package will use this new file as there logging location.  
	 * @param objectClass
	 * @param logFileIdentifier
	 * @return
	 */
	public Logger getClassLogger(Object objectClass, String logFileIdentifier) {
		Logger logger = getClassLogger(objectClass);
		addLogFileHandler(logger, logFileIdentifier);
		return logger;
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
