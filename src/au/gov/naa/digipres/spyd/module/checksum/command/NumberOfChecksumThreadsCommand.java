package au.gov.naa.digipres.spyd.module.checksum.command;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandException;
import au.gov.naa.digipres.spyd.module.ModuleException;
import au.gov.naa.digipres.spyd.module.checksum.ChecksumModule;

public class NumberOfChecksumThreadsCommand extends Command {

	public static final String NUMBER_OF_THREADS_TRIGGER = "numcheckerthreads";

	public NumberOfChecksumThreadsCommand() {
		super();
		trigger = NUMBER_OF_THREADS_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Show/Set the number of checker threads.";
	}

	@Override
	public String getUsage() {
		return getTrigger() + " [<number of threads]";
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		try {
			ChecksumModule mod =
			    (ChecksumModule) getCommandManager().getPluginManager().getModuleManager().getLoadedModule(ChecksumModule.CHECKSUM_MODULE_NAME);

			if (getParamiterList().size() == 0) {
				// Display the number of threads

				fireMessageEvent("Number of Checksum Threads:" + mod.getNumberOfCheckerThreads());
			} else {
				// Change the number
				String param = getParamiterList().get(0);
				try {
					int numThreads = Integer.parseInt(param);
					mod.setNumberOfCheckerThreads(numThreads);
					fireMessageEvent("Number of checksum events changed to '" + numThreads + "'");
				} catch (NumberFormatException nfe) {
					fireMessageEvent("Parameter '" + param + "' isn't a valid integer");
				}
			}
		} catch (ModuleException ex) {
			fireMessageEvent("Cannot find Checksum module!");
		} finally {
			fireEndCommandEvent();
		}
	}

}
