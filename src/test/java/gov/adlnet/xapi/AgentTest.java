package gov.adlnet.xapi;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.*;

import gov.adlnet.xapi.client.AgentClient;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Person;

import junit.framework.TestCase;

public class AgentTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";
	private static final String MBOX = "mailto:test@example.com";
	private String PUT_PROFILE_ID;
    private String POST_PROFILE_ID;

    public void setUp() throws IOException{
        PUT_PROFILE_ID = UUID.randomUUID().toString();
        POST_PROFILE_ID = UUID.randomUUID().toString();

        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        JsonObject puobj = new JsonObject();
        puobj.addProperty("puttest", "puttest");
        assertTrue(client.putAgentProfile(a, PUT_PROFILE_ID, puobj));
        JsonObject pobj = new JsonObject();
        pobj.addProperty("posttest", "posttest");
        assertTrue(client.postAgentProfile(a, POST_PROFILE_ID, pobj));
    }

    public void tearDown() throws IOException{
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        boolean putResp = client.deleteAgentProfile(a, PUT_PROFILE_ID);
        assertTrue(putResp);
        boolean postResp = client.deleteAgentProfile(a, POST_PROFILE_ID);
        assertTrue(postResp);
    }

	public void testGetProfile() throws IOException {
		AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonElement putProfile = client.getAgentProfile(a, PUT_PROFILE_ID);
		assertNotNull(putProfile);
		assertTrue(putProfile.isJsonObject());
		JsonObject obj = (JsonObject)putProfile;
		assertEquals(obj.getAsJsonPrimitive("puttest").getAsString(), "puttest");

        JsonElement postProfile = client.getAgentProfile(a, POST_PROFILE_ID);
        assertNotNull(postProfile);
        assertTrue(postProfile.isJsonObject());
        JsonObject pobj = (JsonObject)postProfile;
        assertEquals(pobj.getAsJsonPrimitive("posttest").getAsString(), "posttest");
	}

    public void testGetProfiles() throws IOException{
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        JsonArray profiles = client.getAgentProfiles(a, "");
        assertNotNull(profiles);
        assertTrue(profiles.size() >= 2);
    }

    public void testGetProfilesWithSince() throws IOException{
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        JsonArray profiles = client.getAgentProfiles(a, "2014-05-02T17:28:47.00Z");
        assertNotNull(profiles);
        assertTrue(profiles.size() >= 2);
    }

    public void testGetPerson() throws IOException{
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        Person p = client.getPerson(a);
        assertNotNull(p);
    }
}
