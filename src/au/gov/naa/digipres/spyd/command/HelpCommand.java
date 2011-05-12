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

public class HelpCommand extends Command {

	public static final String HELP_TRIGGER = "help";

	public HelpCommand() {
		super();
		trigger = HELP_TRIGGER;
	}

	@Override
	public String getHelp() {
		String helpStr = getTrigger() + " - Print the help menu";
		return helpStr;
	}

	@Override
	public String getUsage() {
		return getTrigger() + " [<command>]";
	}

	@Override
	public void runCommand() throws CommandException {
		fireStartCommandEvent();
		if (getParamiterList().size() > 0) {
			// Passed in a parameter, which should be the trigger help is required on.
			boolean foundAValidTigger = false;
			for (String trig : getParamiterList()) {
				if (getCommandManager().hasTrigger(trig)) {
					foundAValidTigger = true;
					fireMessageEvent(getCommandManager().getCommand(trig).getHelp());
					fireMessageEvent(getCommandManager().getCommand(trig).getUsage());
				}
			}
			if (!foundAValidTigger) {
				fireMessageEvent(getCommandManager().getHelpString());
			}
		} else {
			// Print all help.
			fireMessageEvent(getCommandManager().getHelpString());
		}
		fireEndCommandEvent();
	}

}
