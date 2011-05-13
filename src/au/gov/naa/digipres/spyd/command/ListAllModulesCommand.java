package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleManager;

public class ListAllModulesCommand extends Command {

	public static final String LIST_ALL_MODULES_TRIGGER = "listallmodules";

	public ListAllModulesCommand() {
		super();
		trigger = LIST_ALL_MODULES_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - List all the modules.";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();
		fireMessageEvent("LOADED MODULES:");
		for (String mod : moduleManager.listLoadedModules()) {
			fireMessageEvent(mod);
		}

		fireMessageEvent(" ");
		fireMessageEvent("UNLOADED MODULES:");
		for (String mod : moduleManager.listUnloadedModules()) {
			fireMessageEvent(mod);
		}
		fireEndCommandEvent();
	}

}
