package au.gov.naa.digipres.spyd.command;

import java.util.Map;

import au.gov.naa.digipres.spyd.module.ModuleManager;
import au.gov.naa.digipres.spyd.module.ModuleWorkerThread;

public class ListRunningModuleThreadsCommand extends Command {

	public static final String LIST_THREADS_TRIGGER = "runningthreads";

	public ListRunningModuleThreadsCommand() {
		super();
		trigger = LIST_THREADS_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Lists the running module threads and their unique ID";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();
		Map<String, ModuleWorkerThread> threads = moduleManager.getRunningModuleThreads();
		fireMessageEvent("Running Module Threads (Module,  ID):");
		for (String key : threads.keySet()) {
			fireMessageEvent("\t" + threads.get(key).getName() + "\t" + key);
		}

		fireEndCommandEvent();
	}

}
