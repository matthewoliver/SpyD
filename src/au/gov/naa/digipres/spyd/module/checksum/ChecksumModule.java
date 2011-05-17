package au.gov.naa.digipres.spyd.module.checksum;

import java.util.List;
import java.util.Vector;

import au.gov.naa.digipres.spyd.dao.DataAccessManager;
import au.gov.naa.digipres.spyd.dao.ItemRecordDAO;
import au.gov.naa.digipres.spyd.module.ModuleExecutionException;
import au.gov.naa.digipres.spyd.module.ModuleManager;
import au.gov.naa.digipres.spyd.module.SpydModule;

public class ChecksumModule extends SpydModule {

	public static final String PREFERENCE_PERIOD_UNITS = "checksum.period.units";
	public static final String PREFERENCE_PERIOD = "checksum.period";
	public static final String PREFERENCE_CHECKSUM_ALGORITHM = "checksum.algorithm";

	private ItemRecordDAO recordDAO;
	private DataAccessManager dataAccessManager;

	public ChecksumModule(ModuleManager moduleManager) {
		super(moduleManager);

		dataAccessManager = moduleManager.getPluginManager().getSpyd().getDataAccessManager();
		recordDAO = dataAccessManager.getItemRecordDAO();
	}

	@Override
	protected void addCommands() {
		// TODO Auto-generated method stub

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

		return prefs;
	}

}
