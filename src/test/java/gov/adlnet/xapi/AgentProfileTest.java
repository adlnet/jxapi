package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.AgentProfile;

public class AgentProfileTest {

	private static final String NAME = "jXAPI";
	private static final String MBOX = "mailto:test@example.com";
	private static final String PROFILE_ID = "doc_id.1234";
	private Agent agent = null;
	private AgentProfile agentProfile = null;

	@Before
	public void setUp() throws Exception {
		agent = new Agent(NAME, MBOX);
		agentProfile = new AgentProfile(agent, PROFILE_ID);
		JsonObject p = new JsonObject();
		p.addProperty("AgentProfile", "Unit Test");
		agentProfile.setProfile(p);
	}

	@After
	public void tearDown() throws Exception {
		agent = null;
		agentProfile = null;
	}

	@Test
	public void testAgentProfile() {
		AgentProfile agentProfile = new AgentProfile();
		assertNotNull(agentProfile);
	}

	@Test
	public void testAgentProfileAgentString() {
		agentProfile = new AgentProfile(agent, PROFILE_ID);
		assertNotNull(agentProfile);
	}

	@Test
	public void testGetAgent() {
		Agent expected = agent;
		Agent actual = agentProfile.getAgent();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetAgent() {
		Agent expected = new Agent();
		agentProfile.setAgent(expected);
		Agent actual = agentProfile.getAgent();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetProfileId() {
		String expected = PROFILE_ID;
		String actual = agentProfile.getProfileId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetProfileId() {
		String expected = "new." + PROFILE_ID;
		agentProfile.setProfileId(expected);
		String actual = agentProfile.getProfileId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetProfile() {
		JsonObject expected = new JsonObject();
		expected.addProperty("AgentProfile", "Unit Test");
		JsonObject actual = agentProfile.getProfile();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetProfile() {
		JsonObject expected = new JsonObject();
		expected.addProperty("NewAgentProfile", "New Unit Test");
		agentProfile.setProfile(expected);
		JsonObject actual = agentProfile.getProfile();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
