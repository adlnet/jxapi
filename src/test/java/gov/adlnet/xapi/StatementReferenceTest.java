package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.StatementReference;

public class StatementReferenceTest {

	private static final String ID = UUID.randomUUID().toString();
	private StatementReference statementRef = null;

	@Before
	public void setUp() throws Exception {
		statementRef = new StatementReference(ID);
	}

	@After
	public void tearDown() throws Exception {
		statementRef = null;
	}

	@Test
	public void testStatementReference() {
		StatementReference statementRef = new StatementReference();
		assertNotNull(statementRef);
	}

	@Test
	public void testStatementReferenceString() {
		String id = UUID.randomUUID().toString();
		StatementReference statementRef = new StatementReference(id);
		assertNotNull(statementRef);
	}

	@Test
	public void testSerialize() {
		JsonElement actual = statementRef.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assertEquals(obj.getAsJsonPrimitive("objectType").getAsString(), "StatementRef");
		assertEquals(obj.getAsJsonPrimitive("id").getAsString(), ID);
	}

	@Test
	public void testGetObjectType() {
		String expected = "StatementRef";
		String actual = statementRef.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetId() {
		String expected = ID;
		String actual = statementRef.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetId() {
		String expected = UUID.randomUUID().toString();
		statementRef.setId(expected);
		String actual = statementRef.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
