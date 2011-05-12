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
package au.gov.naa.digipres.spyd.core;

import java.io.File;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Constants {
	public static final String ROOT_LOGGING_PACKAGE = "au.gov.naa.digipres";

	public static final String DEFAULT_LOG_FILE_DIR = "log";
	public static final String DEFAULT_LOG_FILE_PATTERN = DEFAULT_LOG_FILE_DIR + File.separator + "spyd%g.log";

	public static Formatter DEFAULT_LOG_FORMATTER = new Formatter() {
		@Override
		public String format(LogRecord record) {
			Date date = new Date(record.getMillis());
			String message = record.getMessage();
			String output = date.toString() + " --> " + message + System.getProperty("line.separator");
			if (record.getThrown() != null) {
				output += "\tCause: " + record.getThrown().getMessage() + System.getProperty("line.separator");
			}
			return output;
		}
	};

}
