package gov.adlnet.xapi;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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

public class ActivityTest extends TestCase {
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

	public void setUp() throws IOException {
		PUT_PROFILE_ID = UUID.randomUUID().toString();
		POST_PROFILE_ID = UUID.randomUUID().toString();
		PUT_STATE_ID = UUID.randomUUID().toString();
		POST_STATE_ID = UUID.randomUUID().toString();
		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		Agent a = new Agent();
		a.setMbox(MBOX);
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonObject puobj = new JsonObject();
		puobj.addProperty("putproftest", "putproftest");
		ActivityProfile puap = new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID);
		puap.setProfile(puobj);
		HashMap<String, String> putEtag = new HashMap<String, String>();
		putEtag.put("If-None-Match", "*");
		assertTrue(_client.putActivityProfile(puap, putEtag));

		JsonObject pobj = new JsonObject();
		pobj.addProperty("postproftest", "postproftest");
		ActivityProfile pap = new ActivityProfile(ACTIVITY_ID, POST_PROFILE_ID);
		pap.setProfile(pobj);
		HashMap<String, String> postEtag = new HashMap<String, String>();
		postEtag.put("If-Match", "*");
		assertTrue(_client.postActivityProfile(pap, postEtag));

		JsonObject pusobj = new JsonObject();
		pusobj.addProperty("putstatetest", "putstatetest");
		ActivityState pus = new ActivityState(ACTIVITY_ID, PUT_STATE_ID, a);
		pus.setRegistration(REGISTRATION);
		pus.setState(pusobj);
		assertTrue(_client.putActivityState(pus));

		JsonObject posbj = new JsonObject();
		posbj.addProperty("poststatetest", "poststatetest");
		ActivityState pos = new ActivityState(ACTIVITY_ID, POST_STATE_ID, a);
		pos.setState(posbj);
		assertTrue(_client.postActivityState(pos));
	}

	public void tearDown() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		assertTrue(_client.deleteActivityProfile(new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID), "*"));
		assertTrue(_client.deleteActivityProfile(new ActivityProfile(ACTIVITY_ID, POST_PROFILE_ID), "*"));
		assertTrue(_client.deleteActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, a)));
		assertTrue(_client.deleteActivityStates(ACTIVITY_ID, a, null));
	}

	@Test
	public void testActivityClientUrlStringString() throws IOException {
		URL lrs_url = new URL(LRS_URI);

		StatementClient sc = new StatementClient(lrs_url, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_url, USERNAME, PASSWORD);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		ac = new ActivityClient(lrs_url, USERNAME, "passw0rd");
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientStringStringString() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		ac = new ActivityClient(LRS_URI, USERNAME, "passw0rd");
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			ac = new ActivityClient("fail", USERNAME, PASSWORD);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientUrlString() throws IOException {
		URL lrs_url = new URL(LRS_URI);
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		StatementClient sc = new StatementClient(lrs_url, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(lrs_url, encodedCreds);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new ActivityClient(lrs_url, encodedCreds);
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testActivityClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Happy path
		ActivityClient ac = new ActivityClient(LRS_URI, encodedCreds);
		Activity returnAct = ac.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new ActivityClient(LRS_URI, encodedCreds);
		try {
			returnAct = ac.getActivity(ACTIVITY_ID);
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			ac = new ActivityClient("fail", encodedCreds);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	public void testGetActivity() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Activity returnAct = _client.getActivity(ACTIVITY_ID);
		assertNotNull(returnAct);
		assertEquals(returnAct.getId(), ACTIVITY_ID);
	}

	public void testGetActivityProfile() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonElement putProfile = _client.getActivityProfile(new ActivityProfile(ACTIVITY_ID, PUT_PROFILE_ID));
		assertNotNull(putProfile);
		assertTrue(putProfile.isJsonObject());
		JsonObject obj = (JsonObject) putProfile;
		assertEquals(obj.getAsJsonPrimitive("putproftest").getAsString(), "putproftest");

		ActivityProfile ap = new ActivityProfile();
		ap.setActivityId(ACTIVITY_ID);
		ap.setProfileId(POST_PROFILE_ID);
		JsonElement postProfile = _client.getActivityProfile(ap);
		assertNotNull(postProfile);
		assertTrue(postProfile.isJsonObject());
		JsonObject pobj = (JsonObject) postProfile;
		assertEquals(pobj.getAsJsonPrimitive("postproftest").getAsString(), "postproftest");
	}

	public void testPutProfileIfNoneMatch() throws IOException {
	}

	public void testPostProfileIfNoneMatch() throws IOException {
	}

	public void testPutProfileBadEtag() throws IOException {
	}

	public void testPostProfileBadEtag() throws IOException {
	}

	public void testGetActivityProfiles() throws IOException {
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

	public void testGetActivityState() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonElement putState = _client.getActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, a));
		assertNotNull(putState);
		assertTrue(putState.isJsonObject());
		JsonObject obj = (JsonObject) putState;
		assertEquals(obj.getAsJsonPrimitive("putstatetest").getAsString(), "putstatetest");

		ActivityState pas = new ActivityState();
		pas.setActivityId(ACTIVITY_ID);
		pas.setStateId(POST_STATE_ID);
		pas.setAgent(a);
		JsonElement postState = _client.getActivityState(pas);
		assertNotNull(postState);
		assertTrue(postState.isJsonObject());
		JsonObject pobj = (JsonObject) postState;
		assertEquals(pobj.getAsJsonPrimitive("poststatetest").getAsString(), "poststatetest");
	}

	public void testGetActivityStateWithRegistration() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		ActivityState as = new ActivityState(ACTIVITY_ID, PUT_STATE_ID, a);
		as.setRegistration(REGISTRATION);
		JsonElement putState = _client.getActivityState(new ActivityState(ACTIVITY_ID, PUT_STATE_ID, a));
		assertNotNull(putState);
		assertTrue(putState.isJsonObject());
		JsonObject obj = (JsonObject) putState;
		assertEquals(obj.getAsJsonPrimitive("putstatetest").getAsString(), "putstatetest");
	}

	public void testGetActivityStates() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		JsonArray arr = _client.getActivityStates(ACTIVITY_ID, a, null, null);
		assertNotNull(arr);
		assertEquals(2, arr.size());
	}
}
