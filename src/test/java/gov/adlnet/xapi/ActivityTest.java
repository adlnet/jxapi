package gov.adlnet.xapi;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;

public class ActivityTest {

	private Activity activity = null;
	
	@Before
	public void setUp() throws Exception {
		String id = "http://example.com/activities/unit-testing";
		ActivityDefinition definition = new ActivityDefinition();
		HashMap<String, String> name = new HashMap<String, String>();
		name.put("en-US", "Unit Testing");
		definition.setName(name);
		activity = new Activity(id, definition);
		assertNotNull(activity);
		
	}

	@After
	public void tearDown() throws Exception {
		activity = null;
	}

	@Test
	public void testActivity() {
		Activity activity = new Activity();
		assertNotNull(activity);
	}

	@Test
	public void testActivityString() {
		String id = "http://example.com/activities/unit-testing";
		Activity activity = new Activity(id);
		assertNotNull(activity);
	}

	@Test
	public void testActivityStringActivityDefinition() {
		String id = "http://example.com/activities/unit-testing";
		ActivityDefinition definition = new ActivityDefinition();
		Activity activity = new Activity(id, definition);
		assertNotNull(activity);
	}

	@Test
	public void testGetObjectType() {
		String expected = "Activity";
		String actual = activity.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetId() {
		String expected = "http://example.com/activities/unit-testing";
		String actual = activity.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetId() {
		String expected = "http://example.com/activities/unit-testing-new";
		activity.setId(expected);
		String actual = activity.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDefinition() {
		ActivityDefinition expected = new ActivityDefinition();
		HashMap<String, String> name = new HashMap<String, String>();
		name.put("en-US", "Unit Testing");
		expected.setName(name);
		ActivityDefinition actual = activity.getDefinition();
		assertNotNull(actual);
		assertEquals(expected.getName().get("en-US"), actual.getName().get("en-US"));
	}

	@Test
	public void testSetDefinition() {
		ActivityDefinition expected = new ActivityDefinition();
		HashMap<String, String> name = new HashMap<String, String>();
		name.put("en-US", "Unit Testing New");
		expected.setName(name);
		activity.setDefinition(expected);
		ActivityDefinition actual = activity.getDefinition();
		assertNotNull(actual);
		assertEquals(expected.getName().get("en-US"), actual.getName().get("en-US"));
	}

	@Test
	public void testSerialize() {
		String expected = "http://example.com/activities/unit-testing";
		JsonElement actual = activity.serialize();
		assertNotNull(actual);
		assert(actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert(obj.get("id").toString().contains(expected));	
	}

	@Test
	public void testToString() {
		String actual = activity.toString();
		assertNotNull(actual);
		
		ActivityDefinition definition = new ActivityDefinition();
		HashMap<String, String> name = null;
		definition.setName(name);
		activity.setDefinition(definition);
		actual = activity.toString();
		assertNotNull(actual);
		
		activity.setDefinition(null);
		actual = activity.toString();
		assertNotNull(actual);
		
	}

	@Test
	public void testToStringString() {
		String actual = activity.toString("en-US");
		assertNotNull(actual);
		
		activity.setDefinition(null);
		actual = activity.toString("en-US");
		assertNotNull(actual);
	}

}
