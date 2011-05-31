/**
* This file is part of Spyd.
*
* Spyd is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Spyd is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Network Manifest Checker.  If not, see <http://www.gnu.org/licenses/>.
*
* @author Matthew Oliver
*/
package au.gov.naa.digipres.spyd.module;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.preferences.PreferencesListener;

public abstract class SpydModule implements PreferencesListener {

	public static final String SPYD_MODULE_ABSTRACT_NAME = "Abstact Module";

	protected ModuleManager moduleManager;

	protected List<Command> moduleCommands;

	protected Queue<String> inputMessages;
	protected Queue<String> outputMessages;

	public SpydModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;

		inputMessages = new LinkedList<String>();
		outputMessages = new LinkedList<String>();
		moduleCommands = new Vector<Command>();
		addCommands();
	}

	/***
	 * Add the commands that relate to this module to the module command list.
	 */
	protected abstract void addCommands();

	public String getName() {
		return SPYD_MODULE_ABSTRACT_NAME;
	}

	/***
	 * Executes a chuck of code, Spyd wraps this execution in a thread.
	 * @throws ModuleExecutionException
	 */
	public abstract void execute() throws ModuleExecutionException;

	/**
	 * Add a message to the end of the input queue. 
	 * @param message
	 */
	public synchronized void addMessage(String message) {
		inputMessages.add(message);
	}

	/***
	 * Get the head message out of the output queue, this action returns and removes the message. 
	 * @return The string at the head of the output queue. 
	 */
	public synchronized String getMessage() {
		return outputMessages.remove();
	}

	public List<Command> getModuleCommands() {
		return moduleCommands;
	}

	/**
	 * Event fired when a module is unladed. Must be implemented to cleaning stop what the module is doing.
	 */
	public abstract void unloadEvent();

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public void preferencesUpdated() {
		// do nothing unless the module overrides this method
	}

}
