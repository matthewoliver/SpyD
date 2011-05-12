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
package au.gov.naa.digipres.spyd.command;

public class VersionCommand extends Command {

	public static final String VERSION_TRIGGER = "version";

	public VersionCommand() {
		trigger = VERSION_TRIGGER;
	}

	@Override
	public String getHelp() {
		return getTrigger() + " - Get the software version number.";
	}

	@Override
	public String getUsage() {
		return getTrigger();
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		fireMessageEvent("Version: BLAH!");
		fireEndCommandEvent();
	}

}
