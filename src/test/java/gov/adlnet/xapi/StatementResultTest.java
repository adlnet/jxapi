package gov.adlnet.xapi;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;

public class StatementResultTest {

	private static final String MORE = "/xapi/statements/more/500c9715f14dee4033dd47664d47cbda";
	private StatementResult statementResult = null;
	private ArrayList<Statement> statements = null;
	

	@Before
	public void setUp() throws Exception {
		statementResult = new StatementResult();
		statements = new ArrayList<Statement>();
		Statement e = new Statement();
		statements.add(e );
		statementResult.setStatements(statements);
		statementResult.setMore(MORE);
	}

	@After
	public void tearDown() throws Exception {
		statementResult = null;
		statements = null;
	}

	@Test
	public void testGetStatements() {
		ArrayList<Statement> expected = statements;
		ArrayList<Statement> actual = statementResult.getStatements();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
	}

	@Test
	public void testSetStatements() {
		ArrayList<Statement> expected = statements;
		Statement s = new Statement();
		expected.add(s);
		statementResult.setStatements(expected);		
		ArrayList<Statement> actual = statementResult.getStatements();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMore() {
		String expected = MORE;
		String actual = statementResult.getMore();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetMore() {
		String expected = "/xapi/statements/more/500c9715f14dee4033dd47664d47new";
		statementResult.setMore(expected);
		String actual = statementResult.getMore();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testHasMore() {
		Boolean expected = true;
		Boolean actual = statementResult.hasMore();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		// more = null
		expected = false;
		statementResult.setMore(null);
		actual = statementResult.hasMore();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		// more sting length = 0
		expected = false;
		statementResult.setMore("");
		actual = statementResult.hasMore();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
	}

}
