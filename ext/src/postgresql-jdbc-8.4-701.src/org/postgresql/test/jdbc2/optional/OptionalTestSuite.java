/*-------------------------------------------------------------------------
*
* Copyright (c) 2004-2008, PostgreSQL Global Development Group
*
* IDENTIFICATION
*   $PostgreSQL: pgjdbc/org/postgresql/test/jdbc2/optional/OptionalTestSuite.java,v 1.9 2008/01/08 06:56:31 jurka Exp $
*
*-------------------------------------------------------------------------
*/
package org.postgresql.test.jdbc2.optional;

import junit.framework.TestSuite;

/**
 * Test suite for the JDBC 2.0 Optional Package implementation.  This
 * includes the DataSource, ConnectionPoolDataSource, and
 * PooledConnection implementations.
 *
 * @author Aaron Mulder (ammulder@chariotsolutions.com)
 */
public class OptionalTestSuite extends TestSuite
{
    /**
     * Gets the test suite for the entire JDBC 2.0 Optional Package
     * implementation.
     */
    public static TestSuite suite() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SimpleDataSourceTest.class);
        suite.addTestSuite(ConnectionPoolTest.class);
        suite.addTestSuite(PoolingDataSourceTest.class);
        return suite;
    }
}
