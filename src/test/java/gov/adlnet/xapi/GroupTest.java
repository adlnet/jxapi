package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Group;

public class GroupTest {

	private static final String NAME = "jXAPI";
	private static final String MBOX = "mailto:test@example.com";
	private Group group = null;
	private ArrayList<Agent> members = null;

	@Before
	public void setUp() throws Exception {
		members = new ArrayList<Agent>();
		members.add(new Agent(NAME, MBOX));
		group = new Group(members);
	}

	@After
	public void tearDown() throws Exception {
		group = null;
		members = null;
	}

	@Test
	public void testGetObjectType() {
		String expected = "Group";
		String actual = group.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() {
		String expected = "New" + NAME;
		members.add(new Agent(expected, MBOX));
		group.setMember(members);
		JsonElement actual = group.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("member").toString().contains(NAME));
		assert (obj.get("member").toString().contains(expected));
	}

	@Test
	public void testToString() {
		members.add(new Agent("", MBOX));
		group.setMember(members);
		String actual = group.toString();
		assertNotNull(actual);
		assert (actual.contains(NAME));
		assert (actual.contains(MBOX));
		assert (actual.contains("Anonymous"));
	}

	@Test
	public void testGroup() {
		ArrayList<Agent> members = new ArrayList<Agent>();
		members.add(new Agent(NAME, MBOX));
		Group actual = new Group(members);
		assertNotNull(actual);
	}

	@Test
	public void testGetMember() {
		ArrayList<Agent> actual = group.getMember();
		assertNotNull(actual);
	}

	@Test
	public void testSetMember() {
		ArrayList<Agent> expected = new ArrayList<Agent>();
		expected.add(new Agent("New" + NAME, MBOX));
		group.setMember(expected);
		ArrayList<Agent> actual = group.getMember();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
