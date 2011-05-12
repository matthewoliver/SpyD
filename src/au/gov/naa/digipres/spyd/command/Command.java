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

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public abstract class Command implements Cloneable {

	// The command the user will use.
	protected String trigger;
	protected List<CommandListener> listeners;
	protected List<String> parameters;
	protected CommandManager commandManager;
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	public static final String END_MESSAGE = "<!END!>";

	public Command() {
		this.trigger = getTrigger();
		listeners = new Vector<CommandListener>();
		parameters = new Vector<String>();
	}

	/**
	 * Returns the help string of the command.
	 * @return
	 */
	public abstract String getHelp();

	/**
	 * Returns the command usage.
	 * @return
	 */
	public abstract String getUsage();

	public abstract void runCommand() throws CommandException;

	public final void runCommandWithEndMessage() throws CommandException {
		runCommand();
		fireMessageEvent(END_MESSAGE);
	}

	public void addParameter(String param) {
		parameters.add(param);
	}

	public void removeParameter(String param) {
		if (parameters.contains(param)) {
			parameters.remove(param);
		}
	}

	public List<String> getParamiterList() {
		return parameters;
	}

	public String getTrigger() {
		return trigger;
	}

	public void registerListener(CommandListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(CommandListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	protected void fireStartCommandEvent() {
		for (CommandListener listener : listeners) {
			listener.commandStarting();
		}
	}

	protected void fireEndCommandEvent() {
		for (CommandListener listener : listeners) {
			listener.commandEnded();
		}
	}

	protected void fireMessageEvent(String message) {
		for (CommandListener listener : listeners) {
			listener.commandMessage(message);
		}
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
