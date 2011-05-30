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
package au.gov.naa.digipres.spyd.dao.hibernate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.PropertyConfigurator;

import au.gov.naa.digipres.spyd.core.Spyd;
import au.gov.naa.digipres.spyd.dao.ConnectionException;
import au.gov.naa.digipres.spyd.dao.DataAccessManager;
import au.gov.naa.digipres.spyd.dao.ItemRecordDAO;

public class HibernateDataAccessManager extends DataAccessManager {

	private Logger logger = spyd.getPluginManager().getCommunicationManager().getClassLogger(this);

	//Map<String, String> connectionProperties = new HashMap<String, String>();

	public static final String DATABASE_USERNAME = "Database User name";
	public static final String DATABASE_PASSWORD = "Database Password";
	public static final String DATABASE_HOSTNAME = "Database Host Name";
	public static final String DATABASE_PORT = "Database Port";
	public static final String DATABASE_NAME = "Database Name";
	public static final String DATABASE_DRIVER_NAME = "Database Driver";
	public static final String DATABASE_CONNECTION_STRING = "Database Connection String";
	public static final String DATABASE_DIALECT = "Database Dialect";
	public static final String HIBERNATE_CONFIGURATION_FILE = "Hibernate Configuration File";

	public HibernateDataAccessManager(Spyd client) {
		super(client);
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Properties props = new Properties();
		try {
			props.load(classLoader.getResourceAsStream("etc/log4j.properties"));
		} catch (NullPointerException ne) {
			// TODO - Verfiy this is the best way of doing this.);
			System.err.println("Could not load etc/log4j.properties; is the root directory of the project or the built jar on the class path?");
			System.err.println("Defaulting to error on standard out for log4J (hibernate) logging.");
			logger.log(Level.SEVERE,
			           "Could not load properties for etc/log4j.properties - is the root directory of the project or the built jar on the class path?");
			logger.log(Level.SEVERE, "Defaulting to error on standard out for log4J (hibernate) logging.");
			initialiseDefaultLogging(props);
		} catch (IOException e) {
			System.err.println("Could not load etc/log4j.properties; IO Exception caught!");
			System.err.println("Defaulting to error on standard out for log4J (hibernate) logging.");
			logger.log(Level.SEVERE, "Could not load properties for etc/log4j.properties - IO Exception caught!");
			logger.log(Level.SEVERE, "Defaulting to error on standard out for log4J (hibernate) logging.");
			e.printStackTrace();
			initialiseDefaultLogging(props);
		}
		PropertyConfigurator.configure(props);

	}

	/**
	 * @param props
	 */
	private void initialiseDefaultLogging(Properties props) {
		//direct log messages to stdout ###
		props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		props.setProperty("log4j.appender.stdout.Target", "System.out");
		props.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
		props.setProperty("log4j.rootLogger", "error, stdout");
		props.setProperty("log4j.logger.org.hibernate", "error");
		props.setProperty("log4j.logger.org.hibernate.SQL", "error");
		props.setProperty("log4j.logger.org.hibernate.type", "error");
		props.setProperty("log4j.logger.org.hibernate.tool.hbm2ddl", "error");
	}

	@Override
	public void connectToDataStore(Map<String, String> connectionProperties) throws ConnectionException {
		HibernateUtil.connectToDB(connectionProperties);
		connected = true;
	}

	@SuppressWarnings("unused")
	@Override
	public void disconnect() throws ConnectionException {
		HibernateUtil.disconnectFromDB();
		connected = false;
	}

	@Override
	public Map<String, String> getConnectionProperties() {
		// TODO Auto-generated method stub
		Map<String, String> connectionProperties = new HashMap<String, String>();
		connectionProperties.put(DATABASE_USERNAME, "");
		connectionProperties.put(DATABASE_PASSWORD, "");
		connectionProperties.put(DATABASE_HOSTNAME, "");
		connectionProperties.put(DATABASE_PORT, "");
		connectionProperties.put(DATABASE_NAME, "");
		connectionProperties.put(DATABASE_DRIVER_NAME, "");
		connectionProperties.put(DATABASE_CONNECTION_STRING, "");
		connectionProperties.put(DATABASE_DIALECT, "");
		connectionProperties.put(HIBERNATE_CONFIGURATION_FILE, "");
		return connectionProperties;
	}

	@Override
	public void beginTransaction() {
		HibernateUtil.beginTransaction();
	}

	@Override
	public void commitTransaction() {
		HibernateUtil.commitTransaction();
	}

	/* (non-Javadoc)
	 * @see au.gov.naa.digipres.nmc.dao.DataAccessManager#getReportDAO()
	 */
	@Override
	public ItemRecordDAO getItemRecordDAO() {
		return new HibernateItemRecordDAO();
	}

	/* (non-Javadoc)
	 * @see au.gov.naa.digipres.nmc.dao.DataAccessManager#rollbackTransaction()
	 */
	@Override
	public void rollbackTransaction() {
		HibernateUtil.rollbackTransaction();
	}
}
