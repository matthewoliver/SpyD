package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleManager;

public class UnloadedModulesCommand extends Command {

	public static final String UNLOADED_MODULES_TRIGGER = "unloaded";

	public UnloadedModulesCommand() {
		super();
		trigger = UNLOADED_MODULES_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - List all the unloaded modules.";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();
		for (String mod : moduleManager.listUnloadedModules()) {
			fireMessageEvent(mod);
		}
		fireEndCommandEvent();
	}

}
