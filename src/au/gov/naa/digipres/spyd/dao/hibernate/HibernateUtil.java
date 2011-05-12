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

/*
 * Created on 5/04/2005
 */
package au.gov.naa.digipres.spyd.dao.hibernate;

import java.sql.DriverManager;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import au.gov.naa.digipres.spyd.dao.ConnectionException;

/**
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	private static final ThreadLocal threadSession = new ThreadLocal();

	@SuppressWarnings("unchecked")
	private static final ThreadLocal threadTransaction = new ThreadLocal();

	public static void connectToDB(Map<String, String> connectionProperties) throws ConnectionException {
		String driverName = connectionProperties.get(HibernateDataAccessManager.DATABASE_DRIVER_NAME);

		String connectionString = connectionProperties.get(HibernateDataAccessManager.DATABASE_CONNECTION_STRING);
		connectionString = substitute(connectionString, "${host}", connectionProperties.get(HibernateDataAccessManager.DATABASE_HOSTNAME));
		connectionString = substitute(connectionString, "${database}", connectionProperties.get(HibernateDataAccessManager.DATABASE_NAME));
		connectionString = substitute(connectionString, "${port}", connectionProperties.get(HibernateDataAccessManager.DATABASE_PORT));

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			throw new ConnectionException("Could not get driver for database", e);
		}

		try {
			DriverManager.getConnection(connectionString, connectionProperties.get(HibernateDataAccessManager.DATABASE_USERNAME),
			                            connectionProperties.get(HibernateDataAccessManager.DATABASE_PASSWORD));

			// Create the SessionFactory
			AnnotationConfiguration configuration = new AnnotationConfiguration();
			configuration.configure(connectionProperties.get(HibernateDataAccessManager.HIBERNATE_CONFIGURATION_FILE));

			configuration.setProperty("hibernate.connection.username", connectionProperties.get(HibernateDataAccessManager.DATABASE_USERNAME)); //$NON-NLS-1$
			configuration.setProperty("hibernate.connection.password", connectionProperties.get(HibernateDataAccessManager.DATABASE_PASSWORD)); //$NON-NLS-1$
			configuration.setProperty("hibernate.connection.url", connectionString); //$NON-NLS-1$ 
			configuration.setProperty("hibernate.connection.driver", connectionProperties.get(HibernateDataAccessManager.DATABASE_DRIVER_NAME));
			configuration.setProperty("hibernate.connection.driver_class", connectionProperties.get(HibernateDataAccessManager.DATABASE_DRIVER_NAME));
			configuration.setProperty("hibernate.dialect", connectionProperties.get(HibernateDataAccessManager.DATABASE_DIALECT));

			configuration.setProperty("hibernate.c3p0.min_size", "5");
			configuration.setProperty("hibernate.c3p0.max_size", "20");
			configuration.setProperty("hibernate.c3p0.timeout", "300");
			configuration.setProperty("hibernate.c3p0.max_statements", "50");
			configuration.setProperty("hibernate.c3p0.idle_test_period", "3000");
			configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
			configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");

			sessionFactory = configuration.buildSessionFactory();

		} catch (Throwable ex) {
			throw new ConnectionException(ex);
		}
	}

	/**
	 * This method is called to completely close a connection with the database. It is
	 * used in testing to allow a single test case object or even a single unit test 
	 * method to close connections in preparation for clearing the databases.
	 * 
	 * Any pending transactions will be rolled back.
	 */
	public static void closeSessionFactory() {
		rollbackTransaction();
		closeSession();
		sessionFactory.close();
		sessionFactory = null;
	}

	/**
	 * This is an alias for the close session factory method. As it implies, it disconnects
	 * from the database.
	 */
	public static void disconnectFromDB() {
		closeSessionFactory();
	}

	/**
	 * Return the session for the current thread. If no session exists then
	 * create a new session for this thread, and add it to the threadSession
	 * field.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Session getSession() throws HibernateException {
		Session s = (Session) threadSession.get();
		if (s == null) {
			if (sessionFactory != null) {
				s = sessionFactory.openSession();
				threadSession.set(s);
			}
		}
		return s;
	}

	/**
	 * This method is a convenience method to ensure that the session for
	 * the current thread is closed. This should be called after a thread
	 * has completed any persistence work.
	 */
	@SuppressWarnings("unchecked")
	public static void closeSession() {
		try {
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				s.close();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void beginTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx == null) {
				tx = getSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Commit the current transaction. Note, this will close the current session.
	 */
	@SuppressWarnings("unchecked")
	public static void commitTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.commit();
				threadTransaction.set(null);
			}
		} catch (HibernateException e) {
			rollbackTransaction();
		}
		// For good measure, we will close the session here.
		// A new session will be created as needed, but this will handle query resource management - no more dangling queries!
		closeSession();
	}

	@SuppressWarnings("unchecked")
	public static void rollbackTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.rollback();
				threadTransaction.set(null);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new IllegalStateException("Exception attempting to rollback a transaction in HibernateUtil class!", e);
		} finally {
			closeSession();
		}
	}

	/**
	 * Substitute unlimited number of times.
	 * @see HibernateDataAccessManager#substitute(String, String, String, int)
	 * @param str String in which to do the substitutions
	 * @param variable The pattern to match and replace
	 * @param value The string to substitute into the string
	 */
	public static String substitute(String str, String variable, String value) {
		return substitute(str, variable, value, -1);
	}

	/**
	 * A utility method for substituting parts of Strings
	* e.g.
	* SubstituteVariable("${xx} brown fox jumped over ${xx} lazy dog",
	*      "${xx}", "the", 1); =>>
	* "the quick brown fox jumped over ${xx} lazy dog"
	* SubstituteVariable("${xx} brown fox jumped over ${xx} lazy dog",
	*      "${xx}", "the"); =>>
	* "the quick brown fox jumped over the lazy dog"
	 * @param str String in which to do the substitutions
	 * @param variable The pattern to match and replace
	 * @param value The string to substitute into the string
	 * @param The maximum number of times to do the substitution. -1 = unlimited.
	 */
	public static String substitute(String str, String variable, String value, int num) {
		int subsLeftToPreform = num;
		StringBuffer buf = new StringBuffer(str);
		int ind = str.indexOf(variable);
		while (ind >= 0 && subsLeftToPreform-- != 0) {
			buf.replace(ind, ind + variable.length(), value);
			ind = buf.toString().indexOf(variable);
		}
		return buf.toString();
	}

	public static void evictFromCache(Object obj, Class<?> clazz, String id) {
		getSession().evict(obj);
		// Not really needed - the second level cache should be disabled...
		sessionFactory.evict(clazz, id);
	}

}
