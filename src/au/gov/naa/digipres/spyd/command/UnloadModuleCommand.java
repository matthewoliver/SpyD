package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleException;
import au.gov.naa.digipres.spyd.module.ModuleManager;

public class UnloadModuleCommand extends Command {

	public static final String UNLOAD_MODULE_TRIGGER = "unload";

	public UnloadModuleCommand() {
		super();
		trigger = UNLOAD_MODULE_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Unload a module";
	}

	@Override
	public String getUsage() {
		return getTrigger() + " <module>";
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		ModuleManager moduleManager = commandManager.getPluginManager().getModuleManager();

		if (getParamiterList().size() > 0) {
			for (String module : getParamiterList()) {
				try {
					moduleManager.unloadModule(module);
				} catch (ModuleException e) {
					fireMessageEvent(e.getMessage());
				}
			}
		} else {
			// Print all help.
			fireMessageEvent(getCommandManager().getHelpString());
		}
		fireEndCommandEvent();
	}

}
