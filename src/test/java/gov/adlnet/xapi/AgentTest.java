package gov.adlnet.xapi;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.*;

import gov.adlnet.xapi.client.AgentClient;
import gov.adlnet.xapi.model.Agent;
import junit.framework.TestCase;

public class AgentTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
	private static final String PASSWORD = "password";
	private static final String MBOX = "mailto:test@example.com";
	private static final String PROFILE_ID = UUID.randomUUID().toString();

	public void testPublishProfile() throws IOException {
		AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonObject obj = new JsonObject();
		obj.addProperty("test", "test");
		client.publishAgentProfile(a, PROFILE_ID, obj);
	}

	public void testGetProfile() throws IOException {
		AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonElement profile = client.getAgentProfile(a, PROFILE_ID);
		assertNotNull(profile);
		assertTrue(profile.isJsonObject());
		JsonObject obj = (JsonObject)profile;
		assertEquals(obj.getAsJsonPrimitive("test").getAsString(), "test");
	}
}
