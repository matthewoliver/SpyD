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

public class CommandException extends Exception {

	private static final long serialVersionUID = 1L;
	protected String trigger;

	public CommandException(Exception e, String trigger) {
		super(e);
		this.trigger = trigger;
	}

	public CommandException(String message, String trigger) {
		super(message);
		this.trigger = trigger;
	}

	public CommandException(String message, Exception e, String trigger) {
		super(message, e);
		this.trigger = trigger;
	}

	public String getTrigger() {
		return trigger;
	}

}
