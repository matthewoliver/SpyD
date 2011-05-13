package au.gov.naa.digipres.spyd.command;

import au.gov.naa.digipres.spyd.module.ModuleException;
import au.gov.naa.digipres.spyd.module.ModuleManager;

public class LoadModuleCommand extends Command {

	public static final String LOAD_MODULE_TRIGGER = "load";

	public LoadModuleCommand() {
		super();
		trigger = LOAD_MODULE_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Load a module.";
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
					moduleManager.loadModule(module);
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
