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
package au.gov.naa.digipres.spyd.plugin;

import java.util.ArrayList;
import java.util.List;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.module.SpydModule;

public abstract class SpydPlugin implements Comparable<SpydPlugin> {

	/***
	 * List of commands that do not relate to a particular module, or is dependant on a module in this plugin.
	 * Commands that only exist while the module is loaded should be added to the module. 
	 * @return A list of commands.
	 */
	public List<Command> getCommands() {
		return new ArrayList<Command>();
	}

	public List<SpydModule> getModules() {
		return new ArrayList<SpydModule>();
	}

	public List<String> getPreferences() {
		return new ArrayList<String>();
	}

	/**
	 * Returns plugin version
	 */
	public abstract String getVersion();

	/**
	 * Returns plugin name
	 */
	public abstract String getName();

	public int compareTo(SpydPlugin comparePlugin) {
		return getName().compareTo(comparePlugin.getName());
	}

}
