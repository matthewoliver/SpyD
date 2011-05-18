package au.gov.naa.digipres.spyd.module.checksum.command;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandException;

public class NumberOfCheckerThreadsCommand extends Command {

	public static final String NUMBER_OF_THREADS_TRIGGER = "numcheckerthreads";

	public NumberOfCheckerThreadsCommand() {
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
		if (getParamiterList().size() == 0) {
			// Display the number of threads
			//TODO get the current value of the numberOfCheckerThreads from the checker module.
		}
		fireEndCommandEvent();
	}

}
