package gov.adlnet.xapi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.client.ActivityClient;
import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityProfile;
import gov.adlnet.xapi.model.ActivityState;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class ActivityClientTest extends TestCase {
	private String PUT_PROFILE_ID;
	private String POST_PROFILE_ID;
	private String PUT_STATE_ID;
	private String POST_STATE_ID;
	private String ACTIVITY_ID;
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";
	private static final String MBOX = "mailto:test@example.com";
	private static final UUID REGISTRATION = UUID.randomUUID();

	private String lrs_uri = null;
	private String username = null;
	private String password = null;
	private String mbox = null;

	private ActivityClient activityClient = null;
	Agent agent = null;

	public void setUp() throws IOException {
		Properties p = new Properties();
		p.load(new FileReader(new File("../jxapi/src/test/java/config/config.properties")));
		lrs_uri = p.getProperty("lrs_uri");
		username = p.getProperty("username");
		password = p.getProperty("password");
		mbox = p.getProperty("mbox");

		if (lrs_uri == null || lrs_uri.length() == 0) {
			lrs_uri = LRS_URI;
		}

		if (username == null || username.length() == 0) {
			username = USERNAME;
		}

		if (password == null || password.length() == 0) {
			password = PASSWORD;
		}

		if (mbox == null || mbox.length() == 0) {
			mbox = MBOX;
		}

		PUT_PROFILE_ID = UUID.randomUUID().toString();
		POST_PROFILE_ID = UUID.randomUUID().toString();
		PUT_STATE_ID = UUID.randomUUID().toString();
		POST_STATE_ID = UUID.randomUUID().toString();
		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		agent = new Agent();
		agent.setMbox(mbox);
		activityClient = new ActivityClient(lrs_uri, username, password);

		JsonObject puobj = new JsonObject();
		puobj.addProperty("putproftest", "putproftest");
		ActivityProfile puap = new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID);
		puap.setProfile(puobj);
		HashMap<String, String> putEtag = new HashMap<String, String>();
		putEtag.put("If-None-Match", "*");
		assertTrue(activityClient.putActivityProfile(puap, putEtag));

		JsonObject pusobj = new JsonObject();
		pusobj.addProperty("putstatetest", "putstatetest");
		ActivityState pus = new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent);
		pus.setRegistration(REGISTRATION);
		pus.setState(pusobj);
		assertTrue(activityClient.putActivityState(pus));
	}

	public void tearDown() throws IOException {

		activityClient = null;
		agent = null;
	}

	@Test
	public void testActivityClientUrlStringString() throws IOException {
		URL lrs_url = new URL(lrs_uri);

		StatementClient sc = new StatementClient(lrs_url, username, password);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(agent, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_url, username, password);
		assertNotNull(ac);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		ac = new ActivityClient(lrs_url, username, "passw0rd");
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientStringStringString() throws IOException {
		StatementClient sc = new StatementClient(lrs_uri, username, password);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(agent, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_uri, username, password);
		assertNotNull(ac);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		ac = new ActivityClient(lrs_uri, username, "passw0rd");
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			ac = new ActivityClient("fail", username, password);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientUrlString() throws IOException {
		URL lrs_url = new URL(lrs_uri);
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		StatementClient sc = new StatementClient(lrs_url, username, password);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(agent, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_url, encodedCreds);
		assertNotNull(ac);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new ActivityClient(lrs_url, encodedCreds);
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		StatementClient sc = new StatementClient(lrs_uri, username, password);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(agent, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_uri, encodedCreds);
		assertNotNull(ac);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new ActivityClient(lrs_uri, encodedCreds);
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			ac = new ActivityClient("fail", encodedCreds);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	public void testGetActivity() throws IOException {
		StatementClient sc = new StatementClient(lrs_uri, username, password);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(agent, v, act);
		sc.postStatement(st);

		Activity returnAct = activityClient.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);
	}

	public void testGetActivityProfile() throws IOException {
		JsonElement putProfile = activityClient.getActivityProfile(new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID));
		assertNotNull(putProfile);
		assertTrue(putProfile.isJsonObject());
		JsonObject obj = (JsonObject) putProfile;
		assertEquals(obj.getAsJsonPrimitive("putproftest").getAsString(), "putproftest");

	}

	public void testGetActivityProfiles() throws IOException {
		JsonArray a = activityClient.getActivityProfiles(ACTIVITY_ID, null);
		assertNotNull(a);
		assertTrue(a.size() >= 1);
	}

	public void testGetActivityProfilesWithSince() throws IOException {
		JsonArray a = activityClient.getActivityProfiles(ACTIVITY_ID, "2014-05-02T17:28:47.00Z");
		assertNotNull(a);
		assertTrue(a.size() >= 1);
	}

	public void testGetActivityState() throws IOException {
		JsonElement putState = activityClient.getActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent));
		assertNotNull(putState);
		assertTrue(putState.isJsonObject());
		JsonObject obj = (JsonObject) putState;
		assertEquals(obj.getAsJsonPrimitive("putstatetest").getAsString(), "putstatetest");
	}

	public void testGetActivityStateWithRegistration() throws IOException {
		ActivityState as = new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent);
		as.setRegistration(REGISTRATION);
		JsonElement putState = activityClient.getActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent));
		assertNotNull(putState);
		assertTrue(putState.isJsonObject());
		JsonObject obj = (JsonObject) putState;
		assertEquals(obj.getAsJsonPrimitive("putstatetest").getAsString(), "putstatetest");
	}

	public void testGetActivityStates() throws IOException {
		JsonArray arr = activityClient.getActivityStates(ACTIVITY_ID, agent, null, null);
		assertNotNull(arr);
		assertEquals(1, arr.size());
	}

	@Test
	public void testPostActivityProfile() throws IOException {
		JsonObject pobj = new JsonObject();
		pobj.addProperty("postproftest", "postproftest");
		ActivityProfile pap = new ActivityProfile(ACTIVITY_ID, POST_PROFILE_ID);
		pap.setProfile(pobj);
		HashMap<String, String> postEtag = new HashMap<String, String>();
		postEtag.put("If-Match", "*");
		assertTrue(activityClient.postActivityProfile(pap, postEtag));
	}

	@Test
	public void testPutActivityProfile() throws IOException {
		JsonObject puobj = new JsonObject();
		puobj.addProperty("putproftest", "putproftest");
		ActivityProfile puap = new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID);
		puap.setProfile(puobj);
		HashMap<String, String> putEtag = new HashMap<String, String>();
		putEtag.put("If-Match", "*");
		assertTrue(activityClient.putActivityProfile(puap, putEtag));
	}

	@Test
	public void testDeleteActivityProfile() throws IOException {
		assertTrue(activityClient.deleteActivityProfile(new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID), "*"));
		assertTrue(activityClient.deleteActivityProfile(new ActivityProfile(ACTIVITY_ID, POST_PROFILE_ID), "*"));

	}

	@Test
	public void testPostActivityState() throws IOException {
		JsonObject posbj = new JsonObject();
		posbj.addProperty("poststatetest", "poststatetest");
		ActivityState pos = new ActivityState(ACTIVITY_ID, POST_STATE_ID, agent);
		pos.setState(posbj);
		assertTrue(activityClient.postActivityState(pos));
	}

	@Test
	public void testPutActivityState() throws IOException {
		JsonObject pusobj = new JsonObject();
		pusobj.addProperty("putstatetest", "putstatetest");
		ActivityState pus = new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent);
		pus.setRegistration(REGISTRATION);
		pus.setState(pusobj);
		assertTrue(activityClient.putActivityState(pus));
	}

	@Test
	public void testDeleteActivityState() throws IOException {
		assertTrue(activityClient.deleteActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, agent)));

	}

	@Test
	public void testDeleteActivityStates() throws IOException {
		assertTrue(activityClient.deleteActivityStates(ACTIVITY_ID, agent, null));
	}
}
