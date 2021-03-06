/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * // Copyright (c) 1998, 2007, Oracle. All rights reserved.
 * 
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package oracle.toplink.essentials.internal.expressions;

import java.io.*;
import java.util.Vector;
import java.util.Collection;

import oracle.toplink.essentials.exceptions.*;
import oracle.toplink.essentials.queryframework.*;
import oracle.toplink.essentials.internal.sessions.AbstractSession;
import oracle.toplink.essentials.internal.databaseaccess.DatabaseCall;

/**
 * @author Andrei Ilitchev
 * @since TOPLink/Java 1.0
 */
public abstract class SQLModifyAllStatementForTempTable extends SQLModifyStatement {
    public static final int CREATE_TEMP_TABLE = 0;
    public static final int INSERT_INTO_TEMP_TABLE = 1;
    public static final int UPDATE_ORIGINAL_TABLE = 2;
    public static final int CLEANUP_TEMP_TABLE = 3;
    
    protected Collection allFields;
    protected Collection primaryKeyFields;
    protected SQLCall selectCall;
    protected int mode;
    
    abstract protected Collection getUsedFields();
    abstract protected void writeUpdateOriginalTable(AbstractSession session, Writer writer) throws IOException;

    public void setAllFields(Collection allFields) {
        this.allFields = allFields;
    }
    public Collection getAllFields() {
        return allFields;
    }
    public void setSelectCall(SQLCall selectCall) {
        this.selectCall = selectCall;
    }
    public SQLCall getSelectCall() {
        return selectCall;
    }
    public void setPrimaryKeyFields(Collection primaryKeyFields) {
        this.primaryKeyFields = primaryKeyFields;
    }
    public Collection getPrimaryKeyFields() {
        return primaryKeyFields;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public int getMode() {
        return mode;
    }

    /**
     * Append the string containing the SQL insert string for the given table.
     */
    public DatabaseCall buildCall(AbstractSession session) {
        SQLCall call = new SQLCall();
        call.returnNothing();
        
        Writer writer = new CharArrayWriter(100);
        
        try {
            if(mode == CREATE_TEMP_TABLE) {
                session.getPlatform().writeCreateTempTableSql(writer, table, session, 
                                                new Vector(getPrimaryKeyFields()),
                                                getUsedFields(),
                                                new Vector(getAllFields()));
            } else if(mode == INSERT_INTO_TEMP_TABLE) {
                session.getPlatform().writeInsertIntoTableSql(writer, table, getUsedFields());

                call.getParameters().addAll(selectCall.getParameters());
                call.getParameterTypes().addAll(selectCall.getParameterTypes());
                
                String selectStr = selectCall.getSQLString();
                writer.write(selectStr);
                
            } else if(mode == UPDATE_ORIGINAL_TABLE) {
                writeUpdateOriginalTable(session, writer);
            } else if(mode == CLEANUP_TEMP_TABLE) {
                session.getPlatform().writeCleanUpTempTableSql(writer, table);
            } else {
                // should never happen
            }

            call.setSQLString(writer.toString());
            
        } catch (IOException exception) {
            throw ValidationException.fileError(exception);
        }
                
        return call;
    }    
}
