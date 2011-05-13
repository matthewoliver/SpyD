package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleManager;

public class LoadedModulesCommand extends Command {

	public static final String LOADED_MODULES_TRIGGER = "loaded";

	public LoadedModulesCommand() {
		super();
		trigger = LOADED_MODULES_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - List all the loaded modules.";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();
		for (String mod : moduleManager.listLoadedModules()) {
			fireMessageEvent(mod);
		}
		fireEndCommandEvent();
	}

}
