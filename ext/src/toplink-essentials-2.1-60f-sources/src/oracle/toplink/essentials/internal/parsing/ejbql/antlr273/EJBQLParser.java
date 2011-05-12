// $ANTLR 2.7.3: "EJBQLParser.g" -> "EJBQLParser.java"$

    package oracle.toplink.essentials.internal.parsing.ejbql.antlr273;

    import java.util.List;
    import java.util.ArrayList;

    import static oracle.toplink.essentials.internal.parsing.NodeFactory.*;
    import oracle.toplink.essentials.exceptions.EJBQLException;

import persistence.antlr.TokenBuffer;
import persistence.antlr.TokenStreamException;
import persistence.antlr.TokenStreamIOException;
import persistence.antlr.ANTLRException;
import persistence.antlr.LLkParser;
import persistence.antlr.Token;
import persistence.antlr.TokenStream;
import persistence.antlr.RecognitionException;
import persistence.antlr.NoViableAltException;
import persistence.antlr.MismatchedTokenException;
import persistence.antlr.SemanticException;
import persistence.antlr.ParserSharedInputState;
import persistence.antlr.collections.impl.BitSet;

/** */
public class EJBQLParser extends oracle.toplink.essentials.internal.parsing.ejbql.EJBQLParser       implements EJBQLTokenTypes
 {

    /** The root node of the parsed EJBQL query. */
    private Object root;

    /** Flag indicating whether aggregates are allowed. */
    private boolean aggregatesAllowed = false;

    /** */
    protected void setAggregatesAllowed(boolean allowed) {
        this.aggregatesAllowed = allowed;
    }

    /** */
    protected boolean aggregatesAllowed() {
        return aggregatesAllowed;
    }

    /** */
    protected void validateAbstractSchemaName(Token token) 
        throws RecognitionException {
        String text = token.getText();
        if (!isValidJavaIdentifier(token.getText())) {
            throw new NoViableAltException(token, getFilename());
        }
    }

    /** */
    protected void validateAttributeName(Token token) 
        throws RecognitionException {
        String text = token.getText();
        if (!isValidJavaIdentifier(token.getText())) {
            throw new NoViableAltException(token, getFilename());
        }
    }

    /** */
    protected boolean isValidJavaIdentifier(String text) {
        if ((text == null) || text.equals(""))
            return false;

        // check first char
        if (!Character.isJavaIdentifierStart(text.charAt(0)))
            return false;

        // check remaining characters
        for (int i = 1; i < text.length(); i++) {
            if (!Character.isJavaIdentifierPart(text.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }

    protected String convertStringLiteral(String text) {
        // skip leading and trailing quotes
        String literal = text.substring(1, text.length() - 1);
        
        // convert ''s to 's
        while (true) {
            int index = literal.indexOf("''");
            if (index == -1) {
                break;
            }
            literal = literal.substring(0, index) + 
                      literal.substring(index + 1, literal.length());
        }

        return literal;
    }

    /** */
    public Object getRootNode() {
        return root;
    }


protected EJBQLParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public EJBQLParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected EJBQLParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public EJBQLParser(TokenStream lexer) {
  this(lexer,3);
}

public EJBQLParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
}

	public final void document() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SELECT:
			{
				root=selectStatement();
				break;
			}
			case UPDATE:
			{
				root=updateStatement();
				break;
			}
			case DELETE:
			{
				root=deleteStatement();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
	}
	
	public final Object  selectStatement() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null;
		Object select, from;
		Object where = null;
		Object groupBy = null;
		Object having = null;
		Object orderBy = null;
		
		
		try {      // for error handling
			select=selectClause();
			from=fromClause();
			{
			switch ( LA(1)) {
			case WHERE:
			{
				where=whereClause();
				break;
			}
			case EOF:
			case GROUP:
			case HAVING:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case GROUP:
			{
				groupBy=groupByClause();
				break;
			}
			case EOF:
			case HAVING:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case HAVING:
			{
				having=havingClause();
				break;
			}
			case EOF:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ORDER:
			{
				orderBy=orderByClause();
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				
				node = factory.newSelectStatement(0, 0, select, from, where, 
				groupBy, having, orderBy); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  updateStatement() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null; 
		Object update, set, where = null;
		
		
		try {      // for error handling
			update=updateClause();
			set=setClause();
			{
			switch ( LA(1)) {
			case WHERE:
			{
				where=whereClause();
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				node = factory.newUpdateStatement(0, 0, update, set, where);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  deleteStatement() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null; 
		Object delete, where = null;
		
		
		try {      // for error handling
			delete=deleteClause();
			{
			switch ( LA(1)) {
			case WHERE:
			{
				where=whereClause();
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				node = factory.newDeleteStatement(0, 0, delete, where);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  selectClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		boolean distinct = false;
		List exprs = new ArrayList();
		
		
		try {      // for error handling
			t = LT(1);
			match(SELECT);
			{
			switch ( LA(1)) {
			case DISTINCT:
			{
				match(DISTINCT);
				if ( inputState.guessing==0 ) {
					distinct = true;
				}
				break;
			}
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case NEW:
			case OBJECT:
			case SUM:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			node=selectExpression();
			if ( inputState.guessing==0 ) {
				exprs.add(node);
			}
			{
			_loop26:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					node=selectExpression();
					if ( inputState.guessing==0 ) {
						exprs.add(node);
					}
				}
				else {
					break _loop26;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
				node = factory.newSelectClause(t.getLine(), t.getColumn(), 
				distinct, exprs); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  fromClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		List varDecls = new ArrayList();
		
		
		try {      // for error handling
			t = LT(1);
			match(FROM);
			identificationVariableDeclaration(varDecls);
			{
			_loop47:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					{
					if (((LA(1) >= ABS && LA(1) <= FLOAT_SUFFIX)) && (LA(2)==AS||LA(2)==IDENT)) {
						identificationVariableDeclaration(varDecls);
					}
					else if ((LA(1)==IN) && (LA(2)==LEFT_ROUND_BRACKET)) {
						node=collectionMemberDeclaration();
						if ( inputState.guessing==0 ) {
							varDecls.add(node);
						}
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
				}
				else {
					break _loop47;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				node = factory.newFromClause(t.getLine(), t.getColumn(), varDecls);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  whereClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		node = null;
		
		try {      // for error handling
			t = LT(1);
			match(WHERE);
			node=conditionalExpression();
			if ( inputState.guessing==0 ) {
				node = factory.newWhereClause(t.getLine(), t.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  groupByClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  g = null;
		
		node = null; 
		List items = new ArrayList();
		
		
		try {      // for error handling
			g = LT(1);
			match(GROUP);
			match(BY);
			node=groupByItem();
			if ( inputState.guessing==0 ) {
				items.add(node);
			}
			{
			_loop163:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					node=groupByItem();
					if ( inputState.guessing==0 ) {
						items.add(node);
					}
				}
				else {
					break _loop163;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				node = factory.newGroupByClause(g.getLine(), g.getColumn(), items);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  havingClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  h = null;
		node = null;
		
		try {      // for error handling
			h = LT(1);
			match(HAVING);
			if ( inputState.guessing==0 ) {
				setAggregatesAllowed(true);
			}
			node=conditionalExpression();
			if ( inputState.guessing==0 ) {
				
				setAggregatesAllowed(false); 
				node = factory.newHavingClause(h.getLine(), h.getColumn(), node);
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  orderByClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  o = null;
		
		node = null; 
		List items = new ArrayList();
		
		
		try {      // for error handling
			o = LT(1);
			match(ORDER);
			match(BY);
			node=orderByItem();
			if ( inputState.guessing==0 ) {
				items.add(node);
			}
			{
			_loop158:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					node=orderByItem();
					if ( inputState.guessing==0 ) {
						items.add(node);
					}
				}
				else {
					break _loop158;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				node = factory.newOrderByClause(o.getLine(), o.getColumn(), items);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  updateClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  u = null;
		Token  ident = null;
		
		node = null; 
		String schema, variable = null;
		
		
		try {      // for error handling
			u = LT(1);
			match(UPDATE);
			schema=abstractSchemaName();
			{
			switch ( LA(1)) {
			case AS:
			case IDENT:
			{
				{
				switch ( LA(1)) {
				case AS:
				{
					match(AS);
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				ident = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					variable = ident.getText();
				}
				break;
			}
			case SET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				node = factory.newUpdateClause(u.getLine(), u.getColumn(), 
				schema, variable); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  setClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		List assignments = new ArrayList();
		
		
		try {      // for error handling
			t = LT(1);
			match(SET);
			node=setAssignmentClause();
			if ( inputState.guessing==0 ) {
				assignments.add(node);
			}
			{
			_loop14:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					node=setAssignmentClause();
					if ( inputState.guessing==0 ) {
						assignments.add(node);
					}
				}
				else {
					break _loop14;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				node = factory.newSetClause(t.getLine(), t.getColumn(), assignments);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final String  abstractSchemaName() throws RecognitionException, TokenStreamException {
		String schema;
		
		Token  ident = null;
		schema = null;
		
		try {      // for error handling
			ident = LT(1);
			matchNot(EOF);
			if ( inputState.guessing==0 ) {
				
				schema = ident.getText();
				validateAbstractSchemaName(ident); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return schema;
	}
	
	public final Object  setAssignmentClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		Object target, value; 
		
		
		try {      // for error handling
			target=setAssignmentTarget();
			t = LT(1);
			match(EQUALS);
			value=newValue();
			if ( inputState.guessing==0 ) {
				
				node = factory.newSetAssignmentClause(t.getLine(), t.getColumn(), 
				target, value); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  setAssignmentTarget() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null;
		Object left = null;
		
		
		try {      // for error handling
			if (((LA(1) >= ABS && LA(1) <= FLOAT_SUFFIX)) && (LA(2)==EQUALS)) {
				node=attribute();
			}
			else if ((LA(1)==IDENT) && (LA(2)==DOT)) {
				node=pathExpression();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  newValue() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  n = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ABS:
			case AVG:
			case CONCAT:
			case COUNT:
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case FALSE:
			case LENGTH:
			case LOCATE:
			case LOWER:
			case MAX:
			case MIN:
			case MOD:
			case SIZE:
			case SQRT:
			case SUBSTRING:
			case SUM:
			case TRIM:
			case TRUE:
			case UPPER:
			case IDENT:
			case LEFT_ROUND_BRACKET:
			case PLUS:
			case MINUS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=simpleArithmeticExpression();
				break;
			}
			case NULL:
			{
				n = LT(1);
				match(NULL);
				if ( inputState.guessing==0 ) {
					node = factory.newNullLiteral(n.getLine(), n.getColumn());
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  attribute() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  i = null;
		node = null;
		
		try {      // for error handling
			i = LT(1);
			matchNot(EOF);
			if ( inputState.guessing==0 ) {
				
				validateAttributeName(i);
				node = factory.newAttribute(i.getLine(), i.getColumn(), i.getText()); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  pathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  d = null;
		
		node = null; 
		Object right;
		
		
		try {      // for error handling
			node=variableAccess();
			{
			int _cnt69=0;
			_loop69:
			do {
				if ((LA(1)==DOT)) {
					d = LT(1);
					match(DOT);
					right=attribute();
					if ( inputState.guessing==0 ) {
						node = factory.newDot(d.getLine(), d.getColumn(), node, right);
					}
				}
				else {
					if ( _cnt69>=1 ) { break _loop69; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt69++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  simpleArithmeticExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  p = null;
		Token  m = null;
		
		node = null; 
		Object right;
		
		
		try {      // for error handling
			node=arithmeticTerm();
			{
			_loop111:
			do {
				switch ( LA(1)) {
				case PLUS:
				{
					p = LT(1);
					match(PLUS);
					right=arithmeticTerm();
					if ( inputState.guessing==0 ) {
						node = factory.newPlus(p.getLine(), p.getColumn(), node, right);
					}
					break;
				}
				case MINUS:
				{
					m = LT(1);
					match(MINUS);
					right=arithmeticTerm();
					if ( inputState.guessing==0 ) {
						node = factory.newMinus(m.getLine(), m.getColumn(), node, right);
					}
					break;
				}
				default:
				{
					break _loop111;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  deleteClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		Token  ident = null;
		
		node = null; 
		String schema, variable = null;
		
		
		try {      // for error handling
			t = LT(1);
			match(DELETE);
			match(FROM);
			schema=abstractSchemaName();
			{
			switch ( LA(1)) {
			case AS:
			case IDENT:
			{
				{
				switch ( LA(1)) {
				case AS:
				{
					match(AS);
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				ident = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					variable = ident.getText();
				}
				break;
			}
			case EOF:
			case WHERE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				node = factory.newDeleteClause(t.getLine(), t.getColumn(), 
				schema, variable); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  selectExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			{
				node=pathExprOrVariableAccess();
				break;
			}
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case SUM:
			{
				node=aggregateExpression();
				break;
			}
			case OBJECT:
			{
				match(OBJECT);
				match(LEFT_ROUND_BRACKET);
				node=variableAccess();
				match(RIGHT_ROUND_BRACKET);
				break;
			}
			case NEW:
			{
				node=constructorExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  pathExprOrVariableAccess() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  d = null;
		
		node = null;
		Object right;
		
		
		try {      // for error handling
			node=variableAccess();
			{
			_loop30:
			do {
				if ((LA(1)==DOT)) {
					d = LT(1);
					match(DOT);
					right=attribute();
					if ( inputState.guessing==0 ) {
						node = factory.newDot(d.getLine(), d.getColumn(), node, right);
					}
				}
				else {
					break _loop30;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  aggregateExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t1 = null;
		Token  t2 = null;
		Token  t3 = null;
		Token  t4 = null;
		Token  t5 = null;
		
		node = null; 
		boolean distinct = false;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case AVG:
			{
				t1 = LT(1);
				match(AVG);
				match(LEFT_ROUND_BRACKET);
				{
				switch ( LA(1)) {
				case DISTINCT:
				{
					match(DISTINCT);
					if ( inputState.guessing==0 ) {
						distinct = true;
					}
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=stateFieldPathExpression();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newAvg(t1.getLine(), t1.getColumn(), distinct, node);
				}
				break;
			}
			case MAX:
			{
				t2 = LT(1);
				match(MAX);
				match(LEFT_ROUND_BRACKET);
				{
				switch ( LA(1)) {
				case DISTINCT:
				{
					match(DISTINCT);
					if ( inputState.guessing==0 ) {
						distinct = true;
					}
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=stateFieldPathExpression();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newMax(t2.getLine(), t2.getColumn(), distinct, node);
				}
				break;
			}
			case MIN:
			{
				t3 = LT(1);
				match(MIN);
				match(LEFT_ROUND_BRACKET);
				{
				switch ( LA(1)) {
				case DISTINCT:
				{
					match(DISTINCT);
					if ( inputState.guessing==0 ) {
						distinct = true;
					}
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=stateFieldPathExpression();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newMin(t3.getLine(), t3.getColumn(), distinct, node);
				}
				break;
			}
			case SUM:
			{
				t4 = LT(1);
				match(SUM);
				match(LEFT_ROUND_BRACKET);
				{
				switch ( LA(1)) {
				case DISTINCT:
				{
					match(DISTINCT);
					if ( inputState.guessing==0 ) {
						distinct = true;
					}
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=stateFieldPathExpression();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newSum(t4.getLine(), t4.getColumn(), distinct, node);
				}
				break;
			}
			case COUNT:
			{
				t5 = LT(1);
				match(COUNT);
				match(LEFT_ROUND_BRACKET);
				{
				switch ( LA(1)) {
				case DISTINCT:
				{
					match(DISTINCT);
					if ( inputState.guessing==0 ) {
						distinct = true;
					}
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=pathExprOrVariableAccess();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newCount(t5.getLine(), t5.getColumn(), distinct, node);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  variableAccess() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  i = null;
		node = null;
		
		try {      // for error handling
			i = LT(1);
			match(IDENT);
			if ( inputState.guessing==0 ) {
				node = factory.newVariableAccess(i.getLine(), i.getColumn(), i.getText());
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  constructorExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		String className = null; 
		List args = new ArrayList();
		
		
		try {      // for error handling
			t = LT(1);
			match(NEW);
			className=constructorName();
			match(LEFT_ROUND_BRACKET);
			node=constructorItem();
			if ( inputState.guessing==0 ) {
				args.add(node);
			}
			{
			_loop39:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					node=constructorItem();
					if ( inputState.guessing==0 ) {
						args.add(node);
					}
				}
				else {
					break _loop39;
				}
				
			} while (true);
			}
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				
				node = factory.newConstructor(t.getLine(), t.getColumn(), 
				className, args); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  stateFieldPathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			node=pathExpression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final String  constructorName() throws RecognitionException, TokenStreamException {
		String className;
		
		Token  i1 = null;
		Token  i2 = null;
		
		className = null;
		StringBuffer buf = new StringBuffer(); 
		
		
		try {      // for error handling
			i1 = LT(1);
			match(IDENT);
			if ( inputState.guessing==0 ) {
				buf.append(i1.getText());
			}
			{
			_loop42:
			do {
				if ((LA(1)==DOT)) {
					match(DOT);
					i2 = LT(1);
					match(IDENT);
					if ( inputState.guessing==0 ) {
						buf.append('.').append(i2.getText());
					}
				}
				else {
					break _loop42;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				className = buf.toString();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return className;
	}
	
	public final Object  constructorItem() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			{
				node=pathExprOrVariableAccess();
				break;
			}
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case SUM:
			{
				node=aggregateExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final void identificationVariableDeclaration(
		List varDecls
	) throws RecognitionException, TokenStreamException {
		
		Object node = null;
		
		try {      // for error handling
			node=rangeVariableDeclaration();
			if ( inputState.guessing==0 ) {
				varDecls.add(node);
			}
			{
			_loop50:
			do {
				if ((LA(1)==INNER||LA(1)==JOIN||LA(1)==LEFT)) {
					node=join();
					if ( inputState.guessing==0 ) {
						varDecls.add(node);
					}
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
	}
	
	public final Object  collectionMemberDeclaration() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		Token  i = null;
		node = null;
		
		try {      // for error handling
			t = LT(1);
			match(IN);
			match(LEFT_ROUND_BRACKET);
			node=collectionValuedPathExpression();
			match(RIGHT_ROUND_BRACKET);
			{
			switch ( LA(1)) {
			case AS:
			{
				match(AS);
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			i = LT(1);
			match(IDENT);
			if ( inputState.guessing==0 ) {
				
				node = factory.newCollectionMemberVariableDecl(
				t.getLine(), t.getColumn(), node, i.getText()); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  rangeVariableDeclaration() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  i = null;
		
		node = null; 
		String schema;
		
		
		try {      // for error handling
			schema=abstractSchemaName();
			{
			switch ( LA(1)) {
			case AS:
			{
				match(AS);
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			i = LT(1);
			match(IDENT);
			if ( inputState.guessing==0 ) {
				
				node = factory.newRangeVariableDecl(i.getLine(), i.getColumn(), 
				schema, i.getText()); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  join() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  i = null;
		Token  t = null;
		
		node = null;
		boolean outerJoin; 
		
		
		try {      // for error handling
			outerJoin=joinSpec();
			{
			switch ( LA(1)) {
			case IDENT:
			{
				node=joinAssociationPathExpression();
				{
				switch ( LA(1)) {
				case AS:
				{
					match(AS);
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				i = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					
					node = factory.newJoinVariableDecl(i.getLine(), i.getColumn(), 
					outerJoin, node, i.getText()); 
					
				}
				break;
			}
			case FETCH:
			{
				t = LT(1);
				match(FETCH);
				node=joinAssociationPathExpression();
				if ( inputState.guessing==0 ) {
					
					node = factory.newFetchJoin(t.getLine(), t.getColumn(), 
					outerJoin, node);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final boolean  joinSpec() throws RecognitionException, TokenStreamException {
		boolean outer;
		
		outer = false;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LEFT:
			{
				match(LEFT);
				{
				switch ( LA(1)) {
				case OUTER:
				{
					match(OUTER);
					break;
				}
				case JOIN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					outer = true;
				}
				break;
			}
			case INNER:
			{
				match(INNER);
				break;
			}
			case JOIN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(JOIN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_23);
			} else {
			  throw ex;
			}
		}
		return outer;
	}
	
	public final Object  joinAssociationPathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  d = null;
		
		node = null; 
		Object left, right;
		
		
		try {      // for error handling
			left=variableAccess();
			d = LT(1);
			match(DOT);
			right=attribute();
			if ( inputState.guessing==0 ) {
				node = factory.newDot(d.getLine(), d.getColumn(), left, right);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  collectionValuedPathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			node=pathExpression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  associationPathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			node=pathExpression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  singleValuedPathExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			node=pathExpression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  conditionalExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		Object right;
		
		
		try {      // for error handling
			node=conditionalTerm();
			{
			_loop75:
			do {
				if ((LA(1)==OR)) {
					t = LT(1);
					match(OR);
					right=conditionalTerm();
					if ( inputState.guessing==0 ) {
						node = factory.newOr(t.getLine(), t.getColumn(), node, right);
					}
				}
				else {
					break _loop75;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  conditionalTerm() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		Object right;
		
		
		try {      // for error handling
			node=conditionalFactor();
			{
			_loop78:
			do {
				if ((LA(1)==AND)) {
					t = LT(1);
					match(AND);
					right=conditionalFactor();
					if ( inputState.guessing==0 ) {
						node = factory.newAnd(t.getLine(), t.getColumn(), node, right);
					}
				}
				else {
					break _loop78;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  conditionalFactor() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  n = null;
		node = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NOT:
			{
				n = LT(1);
				match(NOT);
				break;
			}
			case ABS:
			case AVG:
			case CONCAT:
			case COUNT:
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case EXISTS:
			case FALSE:
			case LENGTH:
			case LOCATE:
			case LOWER:
			case MAX:
			case MIN:
			case MOD:
			case SIZE:
			case SQRT:
			case SUBSTRING:
			case SUM:
			case TRIM:
			case TRUE:
			case UPPER:
			case IDENT:
			case LEFT_ROUND_BRACKET:
			case PLUS:
			case MINUS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ABS:
			case AVG:
			case CONCAT:
			case COUNT:
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case FALSE:
			case LENGTH:
			case LOCATE:
			case LOWER:
			case MAX:
			case MIN:
			case MOD:
			case SIZE:
			case SQRT:
			case SUBSTRING:
			case SUM:
			case TRIM:
			case TRUE:
			case UPPER:
			case IDENT:
			case LEFT_ROUND_BRACKET:
			case PLUS:
			case MINUS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=conditionalPrimary();
				if ( inputState.guessing==0 ) {
					
					if (n != null) {
					node = factory.newNot(n.getLine(), n.getColumn(), node); 
					}
					
				}
				break;
			}
			case EXISTS:
			{
				node=existsExpression((n!=null));
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  conditionalPrimary() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			boolean synPredMatched84 = false;
			if (((LA(1)==LEFT_ROUND_BRACKET) && (_tokenSet_28.member(LA(2))) && (_tokenSet_29.member(LA(3))))) {
				int _m84 = mark();
				synPredMatched84 = true;
				inputState.guessing++;
				try {
					{
					match(LEFT_ROUND_BRACKET);
					conditionalExpression();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched84 = false;
				}
				rewind(_m84);
				inputState.guessing--;
			}
			if ( synPredMatched84 ) {
				match(LEFT_ROUND_BRACKET);
				node=conditionalExpression();
				match(RIGHT_ROUND_BRACKET);
			}
			else if ((_tokenSet_30.member(LA(1))) && (_tokenSet_31.member(LA(2))) && ((LA(3) >= ABS && LA(3) <= FLOAT_SUFFIX))) {
				node=simpleConditionalExpression();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  existsExpression(
		boolean not
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		Object subqueryNode = null; 
		node = null;
		
		
		try {      // for error handling
			t = LT(1);
			match(EXISTS);
			match(LEFT_ROUND_BRACKET);
			subqueryNode=subquery();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				
				node = factory.newExists(t.getLine(), t.getColumn(), 
				not, subqueryNode); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  simpleConditionalExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null; 
		Object left = null;
		
		
		try {      // for error handling
			left=arithmeticExpression();
			node=simpleConditionalExpressionRemainder(left);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  arithmeticExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			if ((_tokenSet_30.member(LA(1))) && (_tokenSet_32.member(LA(2)))) {
				node=simpleArithmeticExpression();
			}
			else if ((LA(1)==LEFT_ROUND_BRACKET) && (LA(2)==SELECT)) {
				match(LEFT_ROUND_BRACKET);
				node=subquery();
				match(RIGHT_ROUND_BRACKET);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  simpleConditionalExpressionRemainder(
		Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  n1 = null;
		Token  n2 = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case EQUALS:
			case NOT_EQUAL_TO:
			case GREATER_THAN:
			case GREATER_THAN_EQUAL_TO:
			case LESS_THAN:
			case LESS_THAN_EQUAL_TO:
			{
				node=comparisonExpression(left);
				break;
			}
			case BETWEEN:
			case IN:
			case LIKE:
			case MEMBER:
			case NOT:
			{
				{
				switch ( LA(1)) {
				case NOT:
				{
					n1 = LT(1);
					match(NOT);
					break;
				}
				case BETWEEN:
				case IN:
				case LIKE:
				case MEMBER:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=conditionWithNotExpression((n1!=null), left);
				break;
			}
			case IS:
			{
				match(IS);
				{
				switch ( LA(1)) {
				case NOT:
				{
					n2 = LT(1);
					match(NOT);
					break;
				}
				case EMPTY:
				case NULL:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				node=isExpression((n2!=null), left);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  comparisonExpression(
		Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t1 = null;
		Token  t2 = null;
		Token  t3 = null;
		Token  t4 = null;
		Token  t5 = null;
		Token  t6 = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case EQUALS:
			{
				t1 = LT(1);
				match(EQUALS);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newEquals(t1.getLine(), t1.getColumn(), left, node);
				}
				break;
			}
			case NOT_EQUAL_TO:
			{
				t2 = LT(1);
				match(NOT_EQUAL_TO);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newNotEquals(t2.getLine(), t2.getColumn(), left, node);
				}
				break;
			}
			case GREATER_THAN:
			{
				t3 = LT(1);
				match(GREATER_THAN);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newGreaterThan(t3.getLine(), t3.getColumn(), left, node);
				}
				break;
			}
			case GREATER_THAN_EQUAL_TO:
			{
				t4 = LT(1);
				match(GREATER_THAN_EQUAL_TO);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newGreaterThanEqual(t4.getLine(), t4.getColumn(), left, node);
				}
				break;
			}
			case LESS_THAN:
			{
				t5 = LT(1);
				match(LESS_THAN);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newLessThan(t5.getLine(), t5.getColumn(), left, node);
				}
				break;
			}
			case LESS_THAN_EQUAL_TO:
			{
				t6 = LT(1);
				match(LESS_THAN_EQUAL_TO);
				node=comparisonExpressionRightOperand();
				if ( inputState.guessing==0 ) {
					node = factory.newLessThanEqual(t6.getLine(), t6.getColumn(), left, node);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  conditionWithNotExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BETWEEN:
			{
				node=betweenExpression(not, left);
				break;
			}
			case LIKE:
			{
				node=likeExpression(not, left);
				break;
			}
			case IN:
			{
				node=inExpression(not, left);
				break;
			}
			case MEMBER:
			{
				node=collectionMemberExpression(not, left);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  isExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NULL:
			{
				node=nullComparisonExpression(not, left);
				break;
			}
			case EMPTY:
			{
				node=emptyCollectionComparisonExpression(not, left);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  betweenExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		Object lower, upper;
		
		
		try {      // for error handling
			t = LT(1);
			match(BETWEEN);
			lower=arithmeticExpression();
			match(AND);
			upper=arithmeticExpression();
			if ( inputState.guessing==0 ) {
				
				node = factory.newBetween(t.getLine(), t.getColumn(),
				not, left, lower, upper);
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  likeExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		Object pattern, escape = null;
		
		
		try {      // for error handling
			t = LT(1);
			match(LIKE);
			pattern=likeValue();
			{
			switch ( LA(1)) {
			case ESCAPE:
			{
				escape=escape();
				break;
			}
			case EOF:
			case AND:
			case GROUP:
			case HAVING:
			case OR:
			case ORDER:
			case RIGHT_ROUND_BRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				node = factory.newLike(t.getLine(), t.getColumn(), not,
				left, pattern, escape);
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  inExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null;
		List items = new ArrayList();
		Object subqueryNode, itemNode;
		
		
		try {      // for error handling
			t = LT(1);
			match(IN);
			match(LEFT_ROUND_BRACKET);
			{
			switch ( LA(1)) {
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				itemNode=inItem();
				if ( inputState.guessing==0 ) {
					items.add(itemNode);
				}
				{
				_loop95:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						itemNode=inItem();
						if ( inputState.guessing==0 ) {
							items.add(itemNode);
						}
					}
					else {
						break _loop95;
					}
					
				} while (true);
				}
				if ( inputState.guessing==0 ) {
					
					node = factory.newIn(t.getLine(), t.getColumn(),
					not, left, items);
					
				}
				break;
			}
			case SELECT:
			{
				subqueryNode=subquery();
				if ( inputState.guessing==0 ) {
					
					node = factory.newIn(t.getLine(), t.getColumn(),
					not, left, subqueryNode);
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RIGHT_ROUND_BRACKET);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  collectionMemberExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		node = null;
		
		try {      // for error handling
			t = LT(1);
			match(MEMBER);
			{
			switch ( LA(1)) {
			case OF:
			{
				match(OF);
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			node=collectionValuedPathExpression();
			if ( inputState.guessing==0 ) {
				
				node = factory.newMemberOf(t.getLine(), t.getColumn(), 
				not, left, node); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  nullComparisonExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		node = null;
		
		try {      // for error handling
			t = LT(1);
			match(NULL);
			if ( inputState.guessing==0 ) {
				node = factory.newIsNull(t.getLine(), t.getColumn(), not, left);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  emptyCollectionComparisonExpression(
		boolean not, Object left
	) throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		node = null;
		
		try {      // for error handling
			t = LT(1);
			match(EMPTY);
			if ( inputState.guessing==0 ) {
				node = factory.newIsEmpty(t.getLine(), t.getColumn(), not, left);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  inItem() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			{
				node=literalNumeric();
				break;
			}
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=inputParameter();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  subquery() throws RecognitionException, TokenStreamException {
		Object node;
		
		
		node = null; 
		Object select, from;
		Object where = null;
		Object groupBy = null;
		Object having = null;
		
		
		try {      // for error handling
			select=simpleSelectClause();
			from=subqueryFromClause();
			{
			switch ( LA(1)) {
			case WHERE:
			{
				where=whereClause();
				break;
			}
			case GROUP:
			case HAVING:
			case RIGHT_ROUND_BRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case GROUP:
			{
				groupBy=groupByClause();
				break;
			}
			case HAVING:
			case RIGHT_ROUND_BRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case HAVING:
			{
				having=havingClause();
				break;
			}
			case RIGHT_ROUND_BRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				node = factory.newSubquery(0, 0, select, from, 
				where, groupBy, having); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  literalString() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  d = null;
		Token  s = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING_LITERAL_DOUBLE_QUOTED:
			{
				d = LT(1);
				match(STRING_LITERAL_DOUBLE_QUOTED);
				if ( inputState.guessing==0 ) {
					
					node = factory.newStringLiteral(d.getLine(), d.getColumn(), 
					convertStringLiteral(d.getText())); 
					
				}
				break;
			}
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				s = LT(1);
				match(STRING_LITERAL_SINGLE_QUOTED);
				if ( inputState.guessing==0 ) {
					
					node = factory.newStringLiteral(s.getLine(), s.getColumn(), 
					convertStringLiteral(s.getText())); 
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  literalNumeric() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  i = null;
		Token  l = null;
		Token  f = null;
		Token  d = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUM_INT:
			{
				i = LT(1);
				match(NUM_INT);
				if ( inputState.guessing==0 ) {
					
					node = factory.newIntegerLiteral(i.getLine(), i.getColumn(), 
					Integer.valueOf(i.getText())); 
					
				}
				break;
			}
			case NUM_LONG:
			{
				l = LT(1);
				match(NUM_LONG);
				if ( inputState.guessing==0 ) {
					
					String text = l.getText();
					// skip the tailing 'l'
					text = text.substring(0, text.length() - 1);
					node = factory.newLongLiteral(l.getLine(), l.getColumn(), 
					Long.valueOf(text)); 
					
				}
				break;
			}
			case NUM_FLOAT:
			{
				f = LT(1);
				match(NUM_FLOAT);
				if ( inputState.guessing==0 ) {
					
					node = factory.newFloatLiteral(f.getLine(), f.getColumn(),
					Float.valueOf(f.getText()));
					
				}
				break;
			}
			case NUM_DOUBLE:
			{
				d = LT(1);
				match(NUM_DOUBLE);
				if ( inputState.guessing==0 ) {
					
					node = factory.newDoubleLiteral(d.getLine(), d.getColumn(),
					Double.valueOf(d.getText()));
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  inputParameter() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  p = null;
		Token  n = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case POSITIONAL_PARAM:
			{
				p = LT(1);
				match(POSITIONAL_PARAM);
				if ( inputState.guessing==0 ) {
					
					// skip the leading ?
					String text = p.getText().substring(1);
					node = factory.newPositionalParameter(p.getLine(), p.getColumn(), text); 
					
				}
				break;
			}
			case NAMED_PARAM:
			{
				n = LT(1);
				match(NAMED_PARAM);
				if ( inputState.guessing==0 ) {
					
					// skip the leading :
					String text = n.getText().substring(1);
					node = factory.newNamedParameter(n.getLine(), n.getColumn(), text); 
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  likeValue() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=inputParameter();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  escape() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		Object escape = null;
		
		
		
		try {      // for error handling
			t = LT(1);
			match(ESCAPE);
			escape=likeValue();
			if ( inputState.guessing==0 ) {
				node = factory.newEscape(t.getLine(), t.getColumn(), escape);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  comparisonExpressionRightOperand() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ABS:
			case AVG:
			case CONCAT:
			case COUNT:
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case FALSE:
			case LENGTH:
			case LOCATE:
			case LOWER:
			case MAX:
			case MIN:
			case MOD:
			case SIZE:
			case SQRT:
			case SUBSTRING:
			case SUM:
			case TRIM:
			case TRUE:
			case UPPER:
			case IDENT:
			case LEFT_ROUND_BRACKET:
			case PLUS:
			case MINUS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=arithmeticExpression();
				break;
			}
			case ALL:
			case ANY:
			case SOME:
			{
				node=anyOrAllExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  anyOrAllExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  a = null;
		Token  y = null;
		Token  s = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ALL:
			{
				a = LT(1);
				match(ALL);
				match(LEFT_ROUND_BRACKET);
				node=subquery();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newAll(a.getLine(), a.getColumn(), node);
				}
				break;
			}
			case ANY:
			{
				y = LT(1);
				match(ANY);
				match(LEFT_ROUND_BRACKET);
				node=subquery();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newAny(y.getLine(), y.getColumn(), node);
				}
				break;
			}
			case SOME:
			{
				s = LT(1);
				match(SOME);
				match(LEFT_ROUND_BRACKET);
				node=subquery();
				match(RIGHT_ROUND_BRACKET);
				if ( inputState.guessing==0 ) {
					node = factory.newSome(s.getLine(), s.getColumn(), node);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  arithmeticTerm() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  m = null;
		Token  d = null;
		
		node = null; 
		Object right;
		
		
		try {      // for error handling
			node=arithmeticFactor();
			{
			_loop114:
			do {
				switch ( LA(1)) {
				case MULTIPLY:
				{
					m = LT(1);
					match(MULTIPLY);
					right=arithmeticFactor();
					if ( inputState.guessing==0 ) {
						node = factory.newMultiply(m.getLine(), m.getColumn(), node, right);
					}
					break;
				}
				case DIVIDE:
				{
					d = LT(1);
					match(DIVIDE);
					right=arithmeticFactor();
					if ( inputState.guessing==0 ) {
						node = factory.newDivide(d.getLine(), d.getColumn(), node, right);
					}
					break;
				}
				default:
				{
					break _loop114;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  arithmeticFactor() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  p = null;
		Token  m = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				p = LT(1);
				match(PLUS);
				node=arithmeticPrimary();
				if ( inputState.guessing==0 ) {
					node = factory.newUnaryPlus(p.getLine(), p.getColumn(), node);
				}
				break;
			}
			case MINUS:
			{
				m = LT(1);
				match(MINUS);
				node=arithmeticPrimary();
				if ( inputState.guessing==0 ) {
					node = factory.newUnaryMinus(m.getLine(), m.getColumn(), node);
				}
				break;
			}
			case ABS:
			case AVG:
			case CONCAT:
			case COUNT:
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case FALSE:
			case LENGTH:
			case LOCATE:
			case LOWER:
			case MAX:
			case MIN:
			case MOD:
			case SIZE:
			case SQRT:
			case SUBSTRING:
			case SUM:
			case TRIM:
			case TRUE:
			case UPPER:
			case IDENT:
			case LEFT_ROUND_BRACKET:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=arithmeticPrimary();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  arithmeticPrimary() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ABS:
			case LENGTH:
			case LOCATE:
			case MOD:
			case SIZE:
			case SQRT:
			{
				node=functionsReturningNumerics();
				break;
			}
			case CURRENT_DATE:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			{
				node=functionsReturningDatetime();
				break;
			}
			case CONCAT:
			case LOWER:
			case SUBSTRING:
			case TRIM:
			case UPPER:
			{
				node=functionsReturningStrings();
				break;
			}
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=inputParameter();
				break;
			}
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			{
				node=literalNumeric();
				break;
			}
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			case FALSE:
			case TRUE:
			{
				node=literalBoolean();
				break;
			}
			case LEFT_ROUND_BRACKET:
			{
				match(LEFT_ROUND_BRACKET);
				node=simpleArithmeticExpression();
				match(RIGHT_ROUND_BRACKET);
				break;
			}
			default:
				if (((_tokenSet_39.member(LA(1))))&&( aggregatesAllowed() )) {
					node=aggregateExpression();
				}
				else if ((LA(1)==IDENT) && (_tokenSet_36.member(LA(2)))) {
					node=variableAccess();
				}
				else if ((LA(1)==IDENT) && (LA(2)==DOT)) {
					node=stateFieldPathExpression();
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  functionsReturningNumerics() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ABS:
			{
				node=abs();
				break;
			}
			case LENGTH:
			{
				node=length();
				break;
			}
			case MOD:
			{
				node=mod();
				break;
			}
			case SQRT:
			{
				node=sqrt();
				break;
			}
			case LOCATE:
			{
				node=locate();
				break;
			}
			case SIZE:
			{
				node=size();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  functionsReturningDatetime() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  d = null;
		Token  t = null;
		Token  ts = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case CURRENT_DATE:
			{
				d = LT(1);
				match(CURRENT_DATE);
				if ( inputState.guessing==0 ) {
					node = factory.newCurrentDate(d.getLine(), d.getColumn());
				}
				break;
			}
			case CURRENT_TIME:
			{
				t = LT(1);
				match(CURRENT_TIME);
				if ( inputState.guessing==0 ) {
					node = factory.newCurrentTime(t.getLine(), t.getColumn());
				}
				break;
			}
			case CURRENT_TIMESTAMP:
			{
				ts = LT(1);
				match(CURRENT_TIMESTAMP);
				if ( inputState.guessing==0 ) {
					node = factory.newCurrentTimestamp(ts.getLine(), ts.getColumn());
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  functionsReturningStrings() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case CONCAT:
			{
				node=concat();
				break;
			}
			case SUBSTRING:
			{
				node=substring();
				break;
			}
			case TRIM:
			{
				node=trim();
				break;
			}
			case UPPER:
			{
				node=upper();
				break;
			}
			case LOWER:
			{
				node=lower();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  literalBoolean() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		Token  f = null;
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TRUE:
			{
				t = LT(1);
				match(TRUE);
				if ( inputState.guessing==0 ) {
					node = factory.newBooleanLiteral(t.getLine(), t.getColumn(), Boolean.TRUE);
				}
				break;
			}
			case FALSE:
			{
				f = LT(1);
				match(FALSE);
				if ( inputState.guessing==0 ) {
					node = factory.newBooleanLiteral(f.getLine(), f.getColumn(), Boolean.FALSE);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  stringPrimary() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			case CONCAT:
			case LOWER:
			case SUBSTRING:
			case TRIM:
			case UPPER:
			{
				node=functionsReturningStrings();
				break;
			}
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=inputParameter();
				break;
			}
			case IDENT:
			{
				node=stateFieldPathExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  literal() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			{
				node=literalNumeric();
				break;
			}
			case FALSE:
			case TRUE:
			{
				node=literalBoolean();
				break;
			}
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  abs() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  a = null;
		node = null;
		
		try {      // for error handling
			a = LT(1);
			match(ABS);
			match(LEFT_ROUND_BRACKET);
			node=simpleArithmeticExpression();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newAbs(a.getLine(), a.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  length() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  l = null;
		node = null;
		
		try {      // for error handling
			l = LT(1);
			match(LENGTH);
			match(LEFT_ROUND_BRACKET);
			node=stringPrimary();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newLength(l.getLine(), l.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  mod() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  m = null;
		
		node = null; 
		Object left, right;
		
		
		try {      // for error handling
			m = LT(1);
			match(MOD);
			match(LEFT_ROUND_BRACKET);
			left=simpleArithmeticExpression();
			match(COMMA);
			right=simpleArithmeticExpression();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newMod(m.getLine(), m.getColumn(), left, right);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  sqrt() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  s = null;
		node = null;
		
		try {      // for error handling
			s = LT(1);
			match(SQRT);
			match(LEFT_ROUND_BRACKET);
			node=simpleArithmeticExpression();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newSqrt(s.getLine(), s.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  locate() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  l = null;
		
		node = null; 
		Object pattern, startPos = null;
		
		
		try {      // for error handling
			l = LT(1);
			match(LOCATE);
			match(LEFT_ROUND_BRACKET);
			pattern=stringPrimary();
			match(COMMA);
			node=stringPrimary();
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				startPos=simpleArithmeticExpression();
				break;
			}
			case RIGHT_ROUND_BRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				
				node = factory.newLocate(l.getLine(), l.getColumn(), 
				pattern, node, startPos); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  size() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  s = null;
		node = null;
		
		try {      // for error handling
			s = LT(1);
			match(SIZE);
			match(LEFT_ROUND_BRACKET);
			node=collectionValuedPathExpression();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newSize(s.getLine(), s.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  concat() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  c = null;
		
		node = null;
		Object firstArg, secondArg;
		
		
		try {      // for error handling
			c = LT(1);
			match(CONCAT);
			match(LEFT_ROUND_BRACKET);
			firstArg=stringPrimary();
			match(COMMA);
			secondArg=stringPrimary();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newConcat(c.getLine(), c.getColumn(), firstArg, secondArg);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  substring() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  s = null;
		
		node = null;
		Object string, start, length;
		
		
		try {      // for error handling
			s = LT(1);
			match(SUBSTRING);
			match(LEFT_ROUND_BRACKET);
			string=stringPrimary();
			match(COMMA);
			start=simpleArithmeticExpression();
			match(COMMA);
			length=simpleArithmeticExpression();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				
				node = factory.newSubstring(s.getLine(), s.getColumn(), 
				string, start, length); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  trim() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  t = null;
		
		node = null; 
		TrimSpecification trimSpec = TrimSpecification.BOTH;
		Object trimChar = null;
		
		
		try {      // for error handling
			t = LT(1);
			match(TRIM);
			match(LEFT_ROUND_BRACKET);
			{
			boolean synPredMatched132 = false;
			if (((_tokenSet_40.member(LA(1))) && (_tokenSet_41.member(LA(2))))) {
				int _m132 = mark();
				synPredMatched132 = true;
				inputState.guessing++;
				try {
					{
					trimSpec();
					trimChar();
					match(FROM);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched132 = false;
				}
				rewind(_m132);
				inputState.guessing--;
			}
			if ( synPredMatched132 ) {
				trimSpec=trimSpec();
				trimChar=trimChar();
				match(FROM);
			}
			else if ((_tokenSet_42.member(LA(1))) && ((LA(2) >= LEFT_ROUND_BRACKET && LA(2) <= DOT))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			node=stringPrimary();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				
				node = factory.newTrim(t.getLine(), t.getColumn(), 
				trimSpec, trimChar, node); 
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  upper() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  u = null;
		node = null;
		
		try {      // for error handling
			u = LT(1);
			match(UPPER);
			match(LEFT_ROUND_BRACKET);
			node=stringPrimary();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newUpper(u.getLine(), u.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  lower() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  l = null;
		node = null;
		
		try {      // for error handling
			l = LT(1);
			match(LOWER);
			match(LEFT_ROUND_BRACKET);
			node=stringPrimary();
			match(RIGHT_ROUND_BRACKET);
			if ( inputState.guessing==0 ) {
				node = factory.newLower(l.getLine(), l.getColumn(), node);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final TrimSpecification  trimSpec() throws RecognitionException, TokenStreamException {
		TrimSpecification trimSpec;
		
		trimSpec = TrimSpecification.BOTH;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LEADING:
			{
				match(LEADING);
				if ( inputState.guessing==0 ) {
					trimSpec = TrimSpecification.LEADING;
				}
				break;
			}
			case TRAILING:
			{
				match(TRAILING);
				if ( inputState.guessing==0 ) {
					trimSpec = TrimSpecification.TRAILING;
				}
				break;
			}
			case BOTH:
			{
				match(BOTH);
				if ( inputState.guessing==0 ) {
					trimSpec = TrimSpecification.BOTH;
				}
				break;
			}
			case FROM:
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		return trimSpec;
	}
	
	public final Object  trimChar() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING_LITERAL_DOUBLE_QUOTED:
			case STRING_LITERAL_SINGLE_QUOTED:
			{
				node=literalString();
				break;
			}
			case POSITIONAL_PARAM:
			case NAMED_PARAM:
			{
				node=inputParameter();
				break;
			}
			case FROM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  simpleSelectClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  s = null;
		
		node = null; 
		boolean distinct = false;
		
		
		try {      // for error handling
			s = LT(1);
			match(SELECT);
			{
			switch ( LA(1)) {
			case DISTINCT:
			{
				match(DISTINCT);
				if ( inputState.guessing==0 ) {
					distinct = true;
				}
				break;
			}
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case SUM:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			node=simpleSelectExpression();
			if ( inputState.guessing==0 ) {
				
				List exprs = new ArrayList();
				exprs.add(node);
				node = factory.newSelectClause(s.getLine(), s.getColumn(), 
				distinct, exprs);
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  subqueryFromClause() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  f = null;
		
		node = null; 
		List varDecls = new ArrayList();
		
		
		try {      // for error handling
			f = LT(1);
			match(FROM);
			subselectIdentificationVariableDeclaration(varDecls);
			{
			_loop153:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					subselectIdentificationVariableDeclaration(varDecls);
				}
				else {
					break _loop153;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				node = factory.newFromClause(f.getLine(), f.getColumn(), varDecls);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  simpleSelectExpression() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			if ((LA(1)==IDENT) && (LA(2)==DOT)) {
				node=singleValuedPathExpression();
			}
			else if ((_tokenSet_39.member(LA(1)))) {
				node=aggregateExpression();
			}
			else if ((LA(1)==IDENT) && (LA(2)==FROM)) {
				node=variableAccess();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final void subselectIdentificationVariableDeclaration(
		List varDecls
	) throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Object node;
		
		try {      // for error handling
			if (((LA(1) >= ABS && LA(1) <= FLOAT_SUFFIX)) && (LA(2)==AS||LA(2)==IDENT)) {
				identificationVariableDeclaration(varDecls);
			}
			else if ((LA(1)==IDENT) && (LA(2)==DOT)) {
				node=associationPathExpression();
				{
				switch ( LA(1)) {
				case AS:
				{
					match(AS);
					break;
				}
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				i = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					
					varDecls.add(factory.newVariableDecl(i.getLine(), i.getColumn(), 
					node, i.getText())); 
					
				}
			}
			else if ((LA(1)==IN) && (LA(2)==LEFT_ROUND_BRACKET)) {
				node=collectionMemberDeclaration();
				if ( inputState.guessing==0 ) {
					varDecls.add(node);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_45);
			} else {
			  throw ex;
			}
		}
	}
	
	public final Object  orderByItem() throws RecognitionException, TokenStreamException {
		Object node;
		
		Token  a = null;
		Token  d = null;
		node = null;
		
		try {      // for error handling
			node=stateFieldPathExpression();
			{
			switch ( LA(1)) {
			case ASC:
			{
				a = LT(1);
				match(ASC);
				if ( inputState.guessing==0 ) {
					node = factory.newAscOrdering(a.getLine(), a.getColumn(), node);
				}
				break;
			}
			case DESC:
			{
				d = LT(1);
				match(DESC);
				if ( inputState.guessing==0 ) {
					node = factory.newDescOrdering(d.getLine(), d.getColumn(), node);
				}
				break;
			}
			case EOF:
			case COMMA:
			{
				if ( inputState.guessing==0 ) {
					node = factory.newAscOrdering(0, 0, node);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	public final Object  groupByItem() throws RecognitionException, TokenStreamException {
		Object node;
		
		node = null;
		
		try {      // for error handling
			if ((LA(1)==IDENT) && (LA(2)==DOT)) {
				node=stateFieldPathExpression();
			}
			else if ((LA(1)==IDENT) && (_tokenSet_47.member(LA(2)))) {
				node=variableAccess();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		return node;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"abs\"",
		"\"all\"",
		"\"and\"",
		"\"any\"",
		"\"as\"",
		"\"asc\"",
		"\"avg\"",
		"\"between\"",
		"\"both\"",
		"\"by\"",
		"\"concat\"",
		"\"count\"",
		"\"current_date\"",
		"\"current_time\"",
		"\"current_timestamp\"",
		"\"desc\"",
		"\"delete\"",
		"\"distinct\"",
		"\"empty\"",
		"\"escape\"",
		"\"exists\"",
		"\"false\"",
		"\"fetch\"",
		"\"from\"",
		"\"group\"",
		"\"having\"",
		"\"in\"",
		"\"inner\"",
		"\"is\"",
		"\"join\"",
		"\"leading\"",
		"\"left\"",
		"\"length\"",
		"\"like\"",
		"\"locate\"",
		"\"lower\"",
		"\"max\"",
		"\"member\"",
		"\"min\"",
		"\"mod\"",
		"\"new\"",
		"\"not\"",
		"\"null\"",
		"\"object\"",
		"\"of\"",
		"\"or\"",
		"\"order\"",
		"\"outer\"",
		"\"select\"",
		"\"set\"",
		"\"size\"",
		"\"sqrt\"",
		"\"some\"",
		"\"substring\"",
		"\"sum\"",
		"\"trailing\"",
		"\"trim\"",
		"\"true\"",
		"\"unknown\"",
		"\"update\"",
		"\"upper\"",
		"\"where\"",
		"IDENT",
		"COMMA",
		"EQUALS",
		"LEFT_ROUND_BRACKET",
		"RIGHT_ROUND_BRACKET",
		"DOT",
		"NOT_EQUAL_TO",
		"GREATER_THAN",
		"GREATER_THAN_EQUAL_TO",
		"LESS_THAN",
		"LESS_THAN_EQUAL_TO",
		"PLUS",
		"MINUS",
		"MULTIPLY",
		"DIVIDE",
		"NUM_INT",
		"NUM_LONG",
		"NUM_FLOAT",
		"NUM_DOUBLE",
		"STRING_LITERAL_DOUBLE_QUOTED",
		"STRING_LITERAL_SINGLE_QUOTED",
		"POSITIONAL_PARAM",
		"NAMED_PARAM",
		"HEX_DIGIT",
		"WS",
		"TEXTCHAR",
		"EXPONENT",
		"FLOAT_SUFFIX"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 134217728L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 1125900712148994L, 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 1125900712148994L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 1125900443713538L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 1125899906842626L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 9007199254740992L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 2L, 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 9007199254741250L, 6L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 2L, 10L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 0L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 1726422100478786L, 131038L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 1726377003322178L, 130910L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 1726376868579394L, 8026L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 134217728L, 8L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 134217728L, 72L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 1726377002797122L, 130906L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 1726377002797122L, 131034L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 1726376869104194L, 130906L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 0L, 32L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 0L, 72L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 1125900712148994L, 74L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 1125945809305602L, 74L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 67108864L, 4L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 1125945809305858L, 78L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 1688850665570370L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 256L, 4L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 1688850665570306L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 3945203645003842576L, 33447973L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { 3949709586462133264L, 33554357L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 3945168460614976528L, 33447973L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 3949709586445356048L, 33554357L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 3946894837483555922L, 33554421L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 1726376868579394L, 8016L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 0L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 1726377011185730L, 130906L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 1726376868579394L, 130906L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 1688850673958978L, 64L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 1726376868579394L, 32602L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 288235873709884416L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 576460769617514496L, 31457280L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 1297037242572750848L, 31457285L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 1297037242438533120L, 31457285L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { 134217728L, 31457280L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { 805306368L, 66L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { 805306368L, 74L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = { 2L, 8L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { 1125900443713538L, 72L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	
	}
