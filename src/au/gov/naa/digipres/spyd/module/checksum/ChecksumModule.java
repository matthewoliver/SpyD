package au.gov.naa.digipres.spyd.module.checksum;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.core.SpydPreferences;
import au.gov.naa.digipres.spyd.dao.DataAccessManager;
import au.gov.naa.digipres.spyd.dao.ItemRecordDAO;
import au.gov.naa.digipres.spyd.module.ModuleExecutionException;
import au.gov.naa.digipres.spyd.module.ModuleManager;
import au.gov.naa.digipres.spyd.module.SpydModule;
import au.gov.naa.digipres.spyd.module.checksum.command.NumberOfChecksumThreadsCommand;

public class ChecksumModule extends SpydModule {

	public static final String CHECKSUM_MODULE_NAME = "checksum";

	public static final String PREFERENCE_PERIOD_UNITS = "checksum.period.units";
	public static final String PREFERENCE_PERIOD = "checksum.period";
	public static final String PREFERENCE_CHECKSUM_ALGORITHM = "checksum.algorithm";
	public static final String PREFERENCE_NUMBER_OF_CHECKER_THREADS = "checksum.num.threads";

	public static final int DEFAULT_NUMBER_OF_CHECKER_THREADS = 2;

	private ItemRecordDAO recordDAO;
	private DataAccessManager dataAccessManager;
	private int numberOfCheckerThreads;

	private List<CheckerThread> checkerThreads;

	// Place the store the commands loeded when these modules are loaded
	private List<Command> moduleCommands;

	private Logger logger;

	// Preferences
	private SpydPreferences preferences;

	public ChecksumModule(ModuleManager moduleManager) {
		super(moduleManager);

		dataAccessManager = moduleManager.getPluginManager().getSpyd().getDataAccessManager();
		recordDAO = dataAccessManager.getItemRecordDAO();

		// Get the number of threads.. that is if the user has specified one in the config file
		preferences = moduleManager.getPluginManager().getSpyd().getPreferences();
		String tmpNumThreads = preferences.getPreference(PREFERENCE_NUMBER_OF_CHECKER_THREADS);
		if (tmpNumThreads == null) {
			// not set so use default.
			numberOfCheckerThreads = DEFAULT_NUMBER_OF_CHECKER_THREADS;
		} else {
			try {
				numberOfCheckerThreads = Integer.parseInt(tmpNumThreads);
			} catch (NumberFormatException ex) {
				logger.warning("checksum.num.threads value is not an int, using default number (" + DEFAULT_NUMBER_OF_CHECKER_THREADS + ")");
				numberOfCheckerThreads = DEFAULT_NUMBER_OF_CHECKER_THREADS;
			}
		}
	}

	/* (non-Javadoc)
	 * @see au.gov.naa.digipres.spyd.module.SpydModule#addCommands()
	 */
	@Override
	protected void addCommands() {
		moduleCommands = new Vector<Command>();

		moduleCommands.add(new NumberOfChecksumThreadsCommand());
	}

	@Override
	public void execute() throws ModuleExecutionException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unloadEvent() {
		// TODO Auto-generated method stub

	}

	public static List<String> getPreferences() {
		List<String> prefs = new Vector<String>();
		prefs.add(PREFERENCE_CHECKSUM_ALGORITHM);
		prefs.add(PREFERENCE_PERIOD);
		prefs.add(PREFERENCE_PERIOD_UNITS);
		prefs.add(PREFERENCE_NUMBER_OF_CHECKER_THREADS);
		return prefs;
	}

	public synchronized void setNumberOfCheckerThreads(int numberOfCheckerThreads) {
		this.numberOfCheckerThreads = numberOfCheckerThreads;
	}

	public synchronized int getNumberOfCheckerThreads() {
		return numberOfCheckerThreads;
	}

	@Override
	public List<Command> getModuleCommands() {
		return moduleCommands;
	}

	/**
	 * Event called when a preivate thread has completed.
	 * @param id The ID of the thread that completed.
	 */
	synchronized void threadCompletedEvent(int id) {

	}

}
