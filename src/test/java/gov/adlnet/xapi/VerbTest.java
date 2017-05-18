package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Verb;

public class VerbTest {

	private static final String ID = "http://example.com/tested";
	private static final String EN_VERB = "tested";
	private static final String ES_VERB = "probado";
	private Verb verb = null;

	@Before
	public void setUp() throws Exception {
		HashMap<String, String> display = new HashMap<String, String>();
		display.put("en-US", EN_VERB);
		display.put("es", ES_VERB);
		verb = new Verb(ID, display);
	}

	@After
	public void tearDown() throws Exception {
		verb = null;
	}

	@Test
	public void testVerb() {
		Verb verb = new Verb();
		assertNotNull(verb);
	}

	@Test
	public void testVerbString() {
		Verb verb = new Verb(ID);
		assertNotNull(verb);
	}

	@Test
	public void testVerbStringHashMapOfStringString() {
		HashMap<String, String> display = new HashMap<String, String>();
		display.put("en-US", EN_VERB);
		Verb verb = new Verb(ID, display);
		assertNotNull(verb);
	}

	@Test
	public void testSerialize() {
		String expected = ID;
		JsonElement actual = verb.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("id").toString().contains(expected));
		assert (obj.get("display").toString().contains(EN_VERB));
		assert (obj.get("display").toString().contains(ES_VERB));
	}

	@Test
	public void testGetId() {
		String expected = ID;
		String actual = verb.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetId() {
		String expected = "http://example.com/changed";
		verb.setId(expected);
		String actual = verb.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDisplay() {
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("en-US", EN_VERB);
		HashMap<String, String> actual = verb.getDisplay();
		assertNotNull(actual);
		assertEquals(expected.get("en-US"), actual.get("en-US"));
	}

	@Test
	public void testSetDisplay() {
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("en-US", "changed");
		verb.setDisplay(expected);
		HashMap<String, String> actual = verb.getDisplay();
		assertNotNull(actual);
		assertEquals(expected.get("en-US"), actual.get("en-US"));
	}

	@Test
	public void testToString() {
		String expected = EN_VERB;
		String actual = verb.toString();
		assert (actual.contains(expected));
	}

	@Test
	public void testToStringString() {
		String expected = EN_VERB;
		String actual = verb.toString("en-US");
		assert (actual.contains(expected));
		actual = verb.toString("es");
		expected = ES_VERB;
		assert (actual.contains(expected));
	}

}
