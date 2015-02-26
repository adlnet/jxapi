package gov.adlnet.xapi;

import gov.adlnet.xapi.client.ActivityClient;
import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.Verb;

import java.io.IOException;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ActivityTest extends TestCase {
	private String PUT_PROFILE_ID;
    private String POST_PROFILE_ID;
    private String PUT_STATE_ID;
    private String POST_STATE_ID;
	private String ACTIVITY_ID;
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";
    private static final String MBOX = "mailto:test@example.com";

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */

	public ActivityTest(String testName) throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException {
		super(testName);

	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ActivityTest.class);
	}

    public void setUp() throws IOException{
        PUT_PROFILE_ID = UUID.randomUUID().toString();
        POST_PROFILE_ID = UUID.randomUUID().toString();
        PUT_STATE_ID = UUID.randomUUID().toString();
        POST_STATE_ID = UUID.randomUUID().toString();
        ACTIVITY_ID = "http://example.com";
        Agent a = new Agent();
        a.setMbox(MBOX);

        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
        JsonObject puobj = new JsonObject();
        puobj.addProperty("putproftest", "putproftest");
        assertTrue(_client.putActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID, puobj));

        JsonObject pobj = new JsonObject();
        pobj.addProperty("postproftest", "postproftest");
        assertTrue(_client.postActivityProfile(ACTIVITY_ID, POST_PROFILE_ID, pobj));

        JsonObject pusobj = new JsonObject();
        pusobj.addProperty("putstatetest", "putstatetest");
        assertTrue(_client.putActivityState(ACTIVITY_ID, a, null, PUT_STATE_ID, pusobj));

        JsonObject posbj = new JsonObject();
        posbj.addProperty("poststatetest", "poststatetest");
        assertTrue(_client.postActivityState(ACTIVITY_ID, a, null, POST_STATE_ID, posbj));
    }

    public void tearDown() throws IOException{
        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        assertTrue(_client.deleteActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID));
        assertTrue(_client.deleteActivityProfile(ACTIVITY_ID, POST_PROFILE_ID));
        assertTrue(_client.deleteActivityState(ACTIVITY_ID, a, null, PUT_STATE_ID));
        assertTrue(_client.deleteActivityStates(ACTIVITY_ID, a, null));
    }

	public void testGetActivity() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        Verb v = new Verb("http://example.com/tested");
        Activity act = new Activity(ACTIVITY_ID);
        Statement st = new Statement(a, v, act);
        String stID = sc.publishStatement(st);

        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Activity returnAct = _client.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);
	}

    public void testGetActivityProfile() throws IOException{
        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
        JsonElement putProfile = _client.getActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID);
        assertNotNull(putProfile);
        assertTrue(putProfile.isJsonObject());
        JsonObject obj = (JsonObject)putProfile;
        assertEquals(obj.getAsJsonPrimitive("putproftest").getAsString(), "putproftest");

        JsonElement postProfile = _client.getActivityProfile(ACTIVITY_ID, POST_PROFILE_ID);
        assertNotNull(postProfile);
        assertTrue(postProfile.isJsonObject());
        JsonObject pobj = (JsonObject)postProfile;
        assertEquals(pobj.getAsJsonPrimitive("postproftest").getAsString(), "postproftest");
    }

	public void testGetActivityProfiles() throws IOException{
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonArray a = _client.getActivityProfiles(ACTIVITY_ID, null);
		assertNotNull(a);
        assertTrue(a.size() >= 2);
	}

	public void testGetActivityProfilesWithSince() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonArray a = _client.getActivityProfiles(ACTIVITY_ID, "2014-05-02T17:28:47.00Z");
		assertNotNull(a);
        assertTrue(a.size() >= 2);
	}
    public void testGetActivityState() throws IOException{
        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        JsonElement putState = _client.getActivityState(ACTIVITY_ID, a, null, PUT_STATE_ID);
        assertNotNull(putState);
        assertTrue(putState.isJsonObject());
        JsonObject obj = (JsonObject)putState;
        assertEquals(obj.getAsJsonPrimitive("putstatetest").getAsString(), "putstatetest");

        JsonElement postState = _client.getActivityState(ACTIVITY_ID, a, null, POST_STATE_ID);
        assertNotNull(postState);
        assertTrue(postState.isJsonObject());
        JsonObject pobj = (JsonObject)postState;
        assertEquals(pobj.getAsJsonPrimitive("poststatetest").getAsString(), "poststatetest");
    }

    public void testGetActivityStates() throws IOException{
        ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
        Agent a = new Agent();
        a.setMbox(MBOX);
        JsonArray arr = _client.getActivityStates(ACTIVITY_ID, a, null, null);
        assertNotNull(arr);
        assertEquals(2, arr.size());
    }
}
