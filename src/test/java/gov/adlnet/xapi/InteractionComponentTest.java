package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.InteractionComponent;

public class InteractionComponentTest {

	private static final String ID = UUID.randomUUID().toString();
	private InteractionComponent interActCmpnt = null;

	@Before
	public void setUp() throws Exception {
		interActCmpnt = new InteractionComponent();
		interActCmpnt.setId(ID);
		HashMap<String, String> description = new HashMap<String, String>();
		description.put("en-US", "Unit Testing.");
		interActCmpnt.setDescription(description);
	}

	@After
	public void tearDown() throws Exception {
		interActCmpnt = null;
	}

	@Test
	public void testGetId() {
		String expected = ID;
		String actual = interActCmpnt.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetId() {
		String expected = "new" + ID;
		interActCmpnt.setId(expected);
		String actual = interActCmpnt.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDescription() {
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("en-US", "Unit Testing.");
		HashMap<String, String> actual = interActCmpnt.getDescription();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetDescription() {
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("en-US", "New Unit Testing.");
		interActCmpnt.setDescription(expected);
		HashMap<String, String> actual = interActCmpnt.getDescription();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() {
		String expected = ID;
		JsonElement actual = interActCmpnt.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("id").toString().contains(expected));
	}
}
