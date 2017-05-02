package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Context;
import gov.adlnet.xapi.model.ContextActivities;
import gov.adlnet.xapi.model.Group;
import gov.adlnet.xapi.model.StatementReference;

public class ContextTest {

	@Test
	public void testSetContextActivities() {
		ContextActivities ca = new ContextActivities();
		ArrayList<Activity> category = new ArrayList<Activity>();
		Activity act = new Activity("http://example.com/" + UUID.randomUUID().toString());
		category.add(act);
		ca.setCategory(category);
		Context c = new Context();
		c.setContextActivities(ca);
		
		ContextActivities actualContextACtivities = c.getContextActivities();
		assertNotNull(actualContextACtivities);
		ArrayList<Activity> actualCategory = actualContextACtivities.getCategory();
		assertNotNull(actualCategory);
		assertEquals(act, actualCategory.get(0));
	}

	@Test
	public void testGetContextActivities() {
		ContextActivities ca = new ContextActivities();
		ArrayList<Activity> category = new ArrayList<Activity>();
		Activity act = new Activity("http://example.com/" + UUID.randomUUID().toString());
		category.add(act);
		ca.setCategory(category);
		Context c = new Context();
		c.setContextActivities(ca);
		
		ContextActivities actualContextACtivities = c.getContextActivities();
		assertNotNull(actualContextACtivities);
		ArrayList<Activity> actualCategory = actualContextACtivities.getCategory();
		assertNotNull(actualCategory);
		assertEquals(act, actualCategory.get(0));
	}

	@Test
	public void testGetRegistration() {
		String expected = UUID.randomUUID().toString();
		Context c = new Context();
		c.setRegistration(expected);
		String actual = c.getRegistration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetRegistration() {
		String expected = UUID.randomUUID().toString();
		Context c = new Context();
		c.setRegistration(expected);
		String actual = c.getRegistration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetInstructor() {
		Agent expected = new Agent("jXAPI", "mailto:test@example.com");
		Context c = new Context();
		c.setInstructor(expected);
		Actor actual = c.getInstructor();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}

	@Test
	public void testSetInstructor() {
		Agent expected = new Agent("jXAPI", "mailto:test@example.com");
		Context c = new Context();
		c.setInstructor(expected);
		Actor actual = c.getInstructor();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}

	@Test
	public void testGetTeam() {
		Agent expected = new Agent("jXAPI", "mailto:test@example.com");
		ArrayList<Agent> members = new ArrayList<Agent>();
		members.add(expected);
		Group group = new Group(members);
		Context c = new Context();
		c.setTeam(group);
		Group actual = c.getTeam();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMember().get(0).getMbox());		
	}

	@Test
	public void testSetTeam() {
		Agent expected = new Agent("jXAPI", "mailto:test@example.com");
		ArrayList<Agent> members = new ArrayList<Agent>();
		members.add(expected);
		Group group = new Group(members);
		Context c = new Context();
		c.setTeam(group);
		Group actual = c.getTeam();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMember().get(0).getMbox());
	}

	@Test
	public void testGetRevision() {
		String expected = UUID.randomUUID().toString();
		Context c = new Context();
		c.setRevision(expected);
		String actual = c.getRevision();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetRevision() {
		String expected = UUID.randomUUID().toString();
		Context c = new Context();
		c.setRevision(expected);
		String actual = c.getRevision();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetPlatform() {
		String expected = "Example virtual meeting software";
		Context c = new Context();
		c.setPlatform(expected);
		String actual = c.getPlatform();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetPlatform() {
		String expected = "Example virtual meeting software";
		Context c = new Context();
		c.setPlatform(expected);
		String actual = c.getPlatform();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetLanguage() {
		String expected = "en-US";
		Context c = new Context();
		c.setLanguage(expected);
		String actual = c.getLanguage();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetLanguage() {
		String expected = "en-US";
		Context c = new Context();
		c.setPlatform(expected);
		String actual = c.getPlatform();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetStatement() {
		StatementReference expected = new StatementReference(UUID.randomUUID().toString());
		Context c = new Context();
		c.setStatement(expected);
		StatementReference actual = c.getStatement();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void testSetStatement() {
		StatementReference expected = new StatementReference(UUID.randomUUID().toString());
		Context c = new Context();
		c.setStatement(expected);
		StatementReference actual = c.getStatement();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void testGetExtensions() {
		HashMap<String, JsonElement> expected = new HashMap<String, JsonElement>();
		String key = "http://example.com/unitTest"+UUID.randomUUID().toString();
		expected.put(key,  new JsonPrimitive("unitTest"));
		Context c = new Context();
		c.setExtensions(expected);
		HashMap<String, JsonElement> actual = c.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testSetExtensions() {
		HashMap<String, JsonElement> expected = new HashMap<String, JsonElement>();
		String key = "http://example.com/unitTest"+UUID.randomUUID().toString();
		expected.put(key,  new JsonPrimitive("unitTest"));
		Context c = new Context();
		c.setExtensions(expected);
		HashMap<String, JsonElement> actual = c.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testSerialize() {
		String id = UUID.randomUUID().toString();
		String revision = UUID.randomUUID().toString();
		String language = "en-US";
		String platform = "Example virtual meeting software";
		ContextActivities ca = new ContextActivities();
		ArrayList<Activity> category = new ArrayList<Activity>();
		Activity act = new Activity("http://example.com/" + UUID.randomUUID().toString());
		category.add(act);
		ca.setCategory(category);
		Agent instructor = new Agent("jXAPI", "mailto:test@example.com");
		Agent team = new Agent("jXAPIteam", "mailto:testTeam@example.com");
		ArrayList<Agent> members = new ArrayList<Agent>();
		members.add(team);
		Group group = new Group(members);
		StatementReference statement = new StatementReference(UUID.randomUUID().toString());
		HashMap<String, JsonElement> extensions = new HashMap<String, JsonElement>();
		String key = UUID.randomUUID().toString();
		JsonObject jo = new JsonObject();
		jo.addProperty("success", true);
		JsonElement je = jo;
		extensions.put(key, je);
		
		Context expected = new Context();
		expected.setRegistration(id);
		expected.setRevision(revision);
		expected.setPlatform(platform);
		expected.setLanguage(language);
		expected.setContextActivities(ca);
		expected.setInstructor(instructor);
		expected.setTeam(group);
		expected.setStatement(statement);
		expected.setExtensions(extensions);
		JsonElement actual = expected.serialize();
		assertNotNull(actual);
		assert(actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assertEquals(obj.getAsJsonPrimitive("registration").getAsString(), id);
	}

}
