package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleManager;

public class IdleModuleThreadsCommand extends Command {

	public static final String IDLE_THREADS_TRIGGER = "idlethreads";

	public IdleModuleThreadsCommand() {
		super();
		trigger = IDLE_THREADS_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Displays the number of idle module threads";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();
		fireMessageEvent("Number of module threads: " + moduleManager.getWorkerPool().size());
		fireEndCommandEvent();
	}

}
