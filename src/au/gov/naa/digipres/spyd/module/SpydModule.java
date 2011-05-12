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

import au.gov.naa.digipres.spyd.core.Spyd;

public abstract class SpydModule {

	public static final String SPYD_MODULE_ABSTRACT_NAME = "Abstact Module";

	protected Spyd spyd;

	public SpydModule(Spyd spyd) {
		this.spyd = spyd;
	}

	public String getName() {
		return SPYD_MODULE_ABSTRACT_NAME;
	}

	/***
	 * Executes a chuck of code, Spyd wraps this execution in a thread.
	 * @throws ModuleExecutionException
	 */
	public abstract void execute() throws ModuleExecutionException;

}
