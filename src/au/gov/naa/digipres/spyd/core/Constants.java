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
	public static final String DEFAULT_LOG_FILE_PATTERN_ENDING = "%g.log";
	public static final String DEFAULT_LOG_FILE_PATTERN = File.separator + "spyd" + DEFAULT_LOG_FILE_PATTERN_ENDING;
	public static final int DEFAULT_LOG_FILE_LIMIT = 1000000;

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

	public static final int STATUS_DIRTY = 0;
	public static final int STATUS_PASSED = 1;
	public static final int STATUS_UNCHECKED = 2;
	public static final int STATUS_FAILED = 3;

	public static final String DEFAULT_CHECKSUM_ALGORITHM = "sha-512";

}
