/*-------------------------------------------------------------------------
*
* Copyright (c) 2004-2008, PostgreSQL Global Development Group
*
* IDENTIFICATION
*   $PostgreSQL: pgjdbc/org/postgresql/ds/PGPooledConnection.java.in,v 1.2 2008/01/08 06:56:27 jurka Exp $
*
*-------------------------------------------------------------------------
*/
package org.postgresql.ds;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.PooledConnection;
import javax.sql.ConnectionEvent;

/**
 * PostgreSQL implementation of the PooledConnection interface.  This shouldn't
 * be used directly, as the pooling client should just interact with the
 * ConnectionPool instead.
 * @see org.postgresql.ds.PGConnectionPoolDataSource
 *
 * @author Aaron Mulder (ammulder@chariotsolutions.com)
 * @author Csaba Nagy (ncsaba@yahoo.com)
 */
public class PGPooledConnection
    extends org.postgresql.ds.jdbc4.AbstractJdbc4PooledConnection
    implements PooledConnection
{

    public PGPooledConnection(Connection con, boolean autoCommit, boolean isXA)
    {
        super(con, autoCommit, isXA);
    }

    public PGPooledConnection(Connection con, boolean autoCommit)
    {
        this(con, autoCommit, false);
    }

    protected ConnectionEvent createConnectionEvent(SQLException sqle)
    {
        return new ConnectionEvent(this, sqle);
    }

}
