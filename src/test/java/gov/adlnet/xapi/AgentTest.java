package gov.adlnet.xapi;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.*;

import gov.adlnet.xapi.client.AgentClient;
import gov.adlnet.xapi.model.Account;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.AgentProfile;
import gov.adlnet.xapi.model.Person;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class AgentTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI";
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
        AgentProfile ap = new AgentProfile(a, PUT_PROFILE_ID);
        ap.setProfile(puobj);
        HashMap<String, String> putEtag = new HashMap<String, String>();
        putEtag.put("If-Match", "*");
        assertTrue(client.putAgentProfile(ap, putEtag));
        JsonObject pobj = new JsonObject();
        pobj.addProperty("posttest", "posttest");
        AgentProfile poap = new AgentProfile(a, POST_PROFILE_ID);
        poap.setProfile(pobj);
        HashMap<String, String> postEtag = new HashMap<String, String>();
        postEtag.put("If-Match", "*");
        assertTrue(client.postAgentProfile(poap, postEtag));
    }

    public void tearDown() throws IOException{
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        boolean putResp = client.deleteAgentProfile(new AgentProfile(a, PUT_PROFILE_ID), "*");
        assertTrue(putResp);
        boolean postResp = client.deleteAgentProfile(new AgentProfile(a, POST_PROFILE_ID), "*");
        assertTrue(postResp);
    }
    
    public void testAgentClient() throws IOException {
    	String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);
    	URL lrs_url = new URL(LRS_URI);
    	
    	AgentClient client = new AgentClient(LRS_URI, encodedCreds);
    	assertNotNull(client);
        
    	// Verify the client can do something.
    	Agent a = new Agent();         
        a.setMbox(MBOX);
        Person p = client.getPerson(a);
        assertNotNull(p);
        assertEquals(p.getMbox()[0], MBOX);
        
        client = null;
        p = null;
        
        client = new AgentClient(lrs_url, encodedCreds);
        assertNotNull(client);
        
        // Verify the client can do something.
        p = client.getPerson(a);
        assertNotNull(p);
        assertEquals(p.getMbox()[0], MBOX);
    }

	public void testGetProfile() throws IOException {
        AgentClient client = new AgentClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonElement putProfile = client.getAgentProfile(new AgentProfile(a, PUT_PROFILE_ID));
		assertNotNull(putProfile);
		assertTrue(putProfile.isJsonObject());
		JsonObject obj = (JsonObject)putProfile;
		assertEquals(obj.getAsJsonPrimitive("puttest").getAsString(), "puttest");

        AgentProfile ap = new AgentProfile();
        ap.setAgent(a);
        ap.setProfileId(POST_PROFILE_ID);
        JsonElement postProfile = client.getAgentProfile(ap);
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

    public void testPutProfileIfNoneMatch() throws IOException{}

    public void testPostProfileIfNoneMatch() throws IOException{}

    public void testPutProfileBadEtag() throws IOException{}

    public void testPostProfileBadEtag() throws IOException{}

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
        assertEquals(p.getMbox()[0], MBOX);
    }
}
