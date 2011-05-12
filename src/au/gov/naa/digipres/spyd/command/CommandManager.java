/**
* This file is part of Network Manifest Checker.
*
* Network Manifest Checker is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Network Manifest Checker is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Network Manifest Checker.  If not, see <http://www.gnu.org/licenses/>.
* 
*/
package au.gov.naa.digipres.spyd.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.gov.naa.digipres.spyd.core.Spyd;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class CommandManager {

	private Map<String, Command> commands;
	private Spyd spyd;
	private PluginManager pluginManager;
	private List<CommandListener> commandlisteners;

	public CommandManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
		this.spyd = pluginManager.getSpyd();
		commands = new HashMap<String, Command>();
		commandlisteners = new java.util.Vector<CommandListener>();
		loadDefaultCommands();
	}

	private void loadDefaultCommands() {
		for (Command command : InitaliseCommands.getCommands()) {
			addCommand(command, true);
		}
	}

	public void addCommands(List<Command> commandList) {
		for (Command command : commandList) {
			addCommand(command, true);
		}
	}

	public void addCommand(Command command, boolean replaceExisting) {
		if (replaceExisting) {
			// Simply add the command, which will replace a command if it already exists. 
			commands.put(command.getTrigger(), command);
			addListenersToNewCommand(command);
			command.setCommandManager(this);
		} else {
			if (!commands.containsKey(command.getTrigger())) {
				commands.put(command.getTrigger(), command);
				addListenersToNewCommand(command);
				command.setCommandManager(this);
			}
		}
	}

	private void addListenersToNewCommand(Command command) {
		for (CommandListener listener : commandlisteners) {
			command.registerListener(listener);
		}
	}

	public void registerCommandListenerToAllCommands(CommandListener listener) {
		// add the listener to our list so we can keep track of them.
		if (!commandlisteners.contains(listener)) {
			commandlisteners.add(listener);

			// add listener to all commands
			for (String trigger : commands.keySet()) {
				commands.get(trigger).registerListener(listener);
			}
		}
	}

	public void removeCommandListenerToAllCommands(CommandListener listener) {
		// add the listener to our list so we can keep track of them.
		if (commandlisteners.contains(listener)) {
			commandlisteners.remove(listener);

			// remove the listener from all commands
			for (String trigger : commands.keySet()) {
				commands.get(trigger).removeListener(listener);
			}
		}
	}

	public String getHelpString() {
		StringBuffer helpStr = new StringBuffer();
		for (String trigger : commands.keySet()) {
			helpStr.append(commands.get(trigger).getHelp() + "\n");
		}

		return helpStr.toString();
	}

	public Command getCommand(String trigger) {
		return cloneCommand(commands.get(trigger));
	}

	protected Command cloneCommand(Command command) {
		try {
			Command clone = command.getClass().newInstance();
			addListenersToNewCommand(clone);
			clone.setCommandManager(this);
			return clone;

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean hasTrigger(String trigger) {
		return commands.containsKey(trigger);
	}

	/**
	 * A special case which is always needed to be checked so it has been implemented.
	 * @param command
	 * @return
	 */
	public boolean isQuitCommand(Command command) {
		return (command instanceof QuitCommand);
	}

	public Spyd getSpyd() {
		return spyd;
	}
}
