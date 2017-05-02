package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import gov.adlnet.xapi.model.ActivityDefinition;
import gov.adlnet.xapi.model.InteractionComponent;

public class ActivityDefinitionTest {

	private ActivityDefinition activityDefinition = null;

	@Before
	public void setUp() throws Exception {
		String key = "en-US";
		String name = "Unit Testing";
		String description = "Unit testing activity definitions.";
		HashMap<String, String> nameMap = new HashMap<String, String>();
		HashMap<String, String> descriptionMap = new HashMap<String, String>();
		nameMap.put(key, name);
		descriptionMap.put(key, description);
		activityDefinition = new ActivityDefinition(nameMap, descriptionMap);
		assertNotNull(activityDefinition);
		String moreInfo = "More unit testing information.";
		activityDefinition.setMoreInfo(moreInfo);
		String type = "http://example.com/activities/unittest";
		activityDefinition.setType(type);
		HashMap<String, JsonElement> extensions = new HashMap<String, JsonElement>();
		key = "http://example.com/testJSONprimitive";
		extensions.put(key, new JsonPrimitive(44));
		JsonObject jo = new JsonObject();
		jo.addProperty("http://example.com/unitTest", "unit test");
		key = "http://example.com/testJSONobject";
		extensions.put(key, jo);
		activityDefinition.setExtensions(extensions);
		String interactionType = "performance";
		activityDefinition.setInteractionType(interactionType);
		ArrayList<String> correctResponsesPattern = new ArrayList<String>();
		correctResponsesPattern.add("true");
		correctResponsesPattern.add("foo");
		activityDefinition.setCorrectResponsesPattern(correctResponsesPattern);
		ArrayList<InteractionComponent> choices = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		choices.add(e);
		activityDefinition.setChoices(choices);
		ArrayList<InteractionComponent> scale = new ArrayList<InteractionComponent>();
		scale.add(e);
		activityDefinition.setScale(scale);
		ArrayList<InteractionComponent> source = new ArrayList<InteractionComponent>();
		source.add(e);
		activityDefinition.setSource(source);
		ArrayList<InteractionComponent> target = new ArrayList<InteractionComponent>();
		target.add(e);
		activityDefinition.setTarget(target);
		ArrayList<InteractionComponent> steps = new ArrayList<InteractionComponent>();
		steps.add(e);
		activityDefinition.setSteps(steps);
	}

	@After
	public void tearDown() throws Exception {
		activityDefinition = null;
	}

	@Test
	public void testActivityDefinition() {
		ActivityDefinition ad = new ActivityDefinition();
		assertNotNull(ad);
	}

	@Test
	public void testActivityDefinitionHashMapOfStringStringHashMapOfStringString() {

		HashMap<String, String> name = new HashMap<String, String>();
		HashMap<String, String> description = new HashMap<String, String>();
		name.put("en-US", "Unit Testing");
		description.put("en-US", "Unit testing activity defintion.");
		ActivityDefinition ad = new ActivityDefinition(name, description);
		assertNotNull(ad);
	}

	@Test
	public void testSerialize() {
		String expected = "Unit Testing";
		JsonElement actual = activityDefinition.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("name").toString().contains(expected));
	}

	@Test
	public void testGetName() {
		String key = "en-US";
		String expected = "Unit Testing";

		HashMap<String, String> actual = activityDefinition.getName();
		assertNotNull(actual);
		assertEquals(expected, actual.get(key));
	}

	@Test
	public void testSetName() {
		String key = "en-US";
		String expected = "New Name";

		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put(key, expected);
		activityDefinition.setName(nameMap);
		HashMap<String, String> actual = activityDefinition.getName();
		assertNotNull(actual);
		assertEquals(expected, actual.get(key));
	}

	@Test
	public void testGetDescription() {
		String key = "en-US";
		String expected = "Unit testing activity definitions.";

		HashMap<String, String> actual = activityDefinition.getDescription();
		assertNotNull(actual);
		assertEquals(expected, actual.get(key));
	}

	@Test
	public void testSetDescription() {
		String key = "en-US";
		String expected = "New unit testing activity definitions.";

		HashMap<String, String> descriptionMap = new HashMap<String, String>();
		descriptionMap.put(key, expected);
		activityDefinition.setDescription(descriptionMap);
		HashMap<String, String> actual = activityDefinition.getDescription();
		assertNotNull(actual);
		assertEquals(expected, actual.get(key));
	}

	@Test
	public void testGetType() {
		String expected = "http://example.com/activities/unittest";
		String actual = activityDefinition.getType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetType() {
		String expected = "http://example.com/activities/unittest";
		activityDefinition.setType(expected);
		String actual = activityDefinition.getType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMoreInfo() {
		String expected = "More unit testing information.";
		String actual = activityDefinition.getMoreInfo();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetMoreInfo() {
		String expected = "New more unit testing information.";
		activityDefinition.setMoreInfo(expected);
		String actual = activityDefinition.getMoreInfo();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetExtensions() {
		HashMap<String, JsonElement> expected = new HashMap<String, JsonElement>();
		String key = "http://example.com/testJSONprimitive";
		expected.put(key, new JsonPrimitive(44));
		HashMap<String, JsonElement> actual = activityDefinition.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testSetExtensions() {
		HashMap<String, JsonElement> expected = new HashMap<String, JsonElement>();
		String key = "http://example.com/testJSONprimitive";
		String value = "testing";
		expected.put(key, new JsonPrimitive(value));
		activityDefinition.setExtensions(expected);
		HashMap<String, JsonElement> actual = activityDefinition.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testGetInteractionType() {
		String expected = "performance";
		String actual = activityDefinition.getInteractionType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetInteractionType() {
		String expected = "other";
		activityDefinition.setInteractionType(expected);
		String actual = activityDefinition.getInteractionType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCorrectResponsesPattern() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("true");
		expected.add("foo");
		ArrayList<String> actual = activityDefinition.getCorrectResponsesPattern();
		assertNotNull(actual);
		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void testSetCorrectResponsesPattern() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("false");
		expected.add("false");
		activityDefinition.setCorrectResponsesPattern(expected);
		ArrayList<String> actual = activityDefinition.getCorrectResponsesPattern();
		assertNotNull(actual);
		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void testGetChoices() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		ArrayList<InteractionComponent> actual = activityDefinition.getChoices();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testSetChoices() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "false";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		activityDefinition.setChoices(expected);
		ArrayList<InteractionComponent> actual = activityDefinition.getChoices();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testGetScale() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		ArrayList<InteractionComponent> actual = activityDefinition.getScale();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testSetScale() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "false";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		activityDefinition.setScale(expected);
		ArrayList<InteractionComponent> actual = activityDefinition.getScale();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testGetSource() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		ArrayList<InteractionComponent> actual = activityDefinition.getSource();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testSetSource() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "false";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		activityDefinition.setSource(expected);
		ArrayList<InteractionComponent> actual = activityDefinition.getSource();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testGetTarget() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		ArrayList<InteractionComponent> actual = activityDefinition.getTarget();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testSetTarget() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "false";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		activityDefinition.setTarget(expected);
		ArrayList<InteractionComponent> actual = activityDefinition.getTarget();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testGetSteps() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "true";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		ArrayList<InteractionComponent> actual = activityDefinition.getSteps();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testSetSteps() {
		ArrayList<InteractionComponent> expected = new ArrayList<InteractionComponent>();
		InteractionComponent e = new InteractionComponent();
		String id = "false";
		e.setId(id);
		HashMap<String, String> desc = new HashMap<String, String>();
		desc.put("en-US", "test example.");
		e.setDescription(desc);
		expected.add(e);
		activityDefinition.setSteps(expected);
		ArrayList<InteractionComponent> actual = activityDefinition.getSteps();
		assertNotNull(actual);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void testToString() {
		String actual = activityDefinition.toString();
		assertNotNull(actual);

		activityDefinition = null;
		activityDefinition = new ActivityDefinition();
		actual = activityDefinition.toString();
		System.out.println(actual);
		assertEquals(0, actual.length());
	}

	@Test
	public void testToStringString() {
		String actual = activityDefinition.toString("en-US");
		assertNotNull(actual);

		activityDefinition = null;
		activityDefinition = new ActivityDefinition();
		actual = activityDefinition.toString("en-US");
		assertEquals(0, actual.length());
	}

}
