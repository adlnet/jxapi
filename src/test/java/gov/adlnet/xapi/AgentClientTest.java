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

import gov.adlnet.xapi.client.AgentClient;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.AgentProfile;
import gov.adlnet.xapi.model.Person;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class AgentClientTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";
	private static final String MBOX = "mailto:test@example.com";
	private String PUT_PROFILE_ID;
	private String POST_PROFILE_ID;
	
	private String lrs_uri = null;
	private String username = null;
	private String password = null;
	private String mbox = null;

	public void setUp() throws IOException {
		
		Properties props = new Properties();
		props.load(new FileReader(new File("../jxapi/src/test/java/config/config.properties")));
		lrs_uri = props.getProperty("lrs_uri");
		username = props.getProperty("username");
		password = props.getProperty("password");
		mbox = props.getProperty("mbox");


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
		
		AgentClient client = new AgentClient(lrs_uri, username, password);
		
		Agent a = new Agent();
		a.setMbox(mbox);
		Person peep = client.getPerson(a);
		client.getAgentProfiles(a, "2014-05-02T17:28:47.00Z");
		JsonObject puobj = new JsonObject();
		puobj.addProperty("puttest", "puttest");
		AgentProfile ap = new AgentProfile(a, PUT_PROFILE_ID);
		ap.setProfile(puobj);
		HashMap<String, String> putEtag = new HashMap<String, String>();
		putEtag.put("If-None-Match", "*");
		assertTrue(client.putAgentProfile(ap, putEtag));
		JsonObject pobj = new JsonObject();
		pobj.addProperty("posttest", "posttest");
		AgentProfile poap = new AgentProfile(a, POST_PROFILE_ID);
		poap.setProfile(pobj);
		HashMap<String, String> postEtag = new HashMap<String, String>();
		postEtag.put("If-Match", "*");
		assertTrue(client.postAgentProfile(poap, postEtag));
	}

	public void tearDown() throws IOException {
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		boolean putResp = client.deleteAgentProfile(new AgentProfile(a, PUT_PROFILE_ID), "*");
		assertTrue(putResp);
		boolean postResp = client.deleteAgentProfile(new AgentProfile(a, POST_PROFILE_ID), "*");
		assertTrue(postResp);
	}

	@Test
	public void testAgentClientStringStringString() throws IOException {

		// Happy path
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		Person p = client.getPerson(a);
		assertNotNull(p);
		assertEquals(p.getMbox()[0], mbox);

		// Incorrect password
		client = new AgentClient(lrs_uri, username, "passw0rd");
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			client = new AgentClient("fail", username, password);
		} catch (Exception e) {
			assertTrue(true);
		}
		assertNotNull(client);
	}

	@Test
	public void testAgentClientURLStringString() throws IOException {
		URL lrs_url = new URL(lrs_uri);

		// Happy path
		AgentClient client = new AgentClient(lrs_url, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		Person p = null;
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			fail("Exception " + e.toString());
		}
		assertNotNull(p);
		assertEquals(p.getMbox()[0], mbox);

		// Incorrect password
		client = new AgentClient(lrs_url, username, "passw0rd");
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAgentClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		// Happy path
		AgentClient client = new AgentClient(lrs_uri, encodedCreds);
		Agent a = new Agent();
		a.setMbox(mbox);
		Person p = null;
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			fail("Exception " + e.toString());
		}
		assertNotNull(p);
		assertEquals(p.getMbox()[0], mbox);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		client = new AgentClient(lrs_uri, encodedCreds);
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			client = new AgentClient("fail", encodedCreds);
		} catch (Exception e) {
			assertTrue(true);
		}
		assertNotNull(client);
	}

	@Test
	public void testAgentClientURLString() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
		URL lrs_url = new URL(lrs_uri);

		// Happy path
		AgentClient client = new AgentClient(lrs_url, encodedCreds);
		Agent a = new Agent();
		a.setMbox(mbox);
		Person p = null;
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			fail("Exception " + e.toString());
		}
		assertNotNull(p);
		assertEquals(p.getMbox()[0], mbox);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		client = new AgentClient(lrs_url, encodedCreds);
		try {
			p = client.getPerson(a);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	public void testGetProfile() throws IOException {
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		JsonElement putProfile = client.getAgentProfile(new AgentProfile(a, PUT_PROFILE_ID));
		assertNotNull(putProfile);
		assertTrue(putProfile.isJsonObject());
		JsonObject obj = (JsonObject) putProfile;
		assertEquals(obj.getAsJsonPrimitive("puttest").getAsString(), "puttest");

		AgentProfile ap = new AgentProfile();
		ap.setAgent(a);
		ap.setProfileId(POST_PROFILE_ID);
		JsonElement postProfile = client.getAgentProfile(ap);
		assertNotNull(postProfile);
		assertTrue(postProfile.isJsonObject());
		JsonObject pobj = (JsonObject) postProfile;
		assertEquals(pobj.getAsJsonPrimitive("posttest").getAsString(), "posttest");
	}

	public void testGetProfiles() throws IOException {
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		JsonArray profiles = client.getAgentProfiles(a, "");
		assertNotNull(profiles);
		assertTrue(profiles.size() >= 2);
	}

	public void testPutProfileIfNoneMatch() throws IOException {
	}

	public void testPostProfileIfNoneMatch() throws IOException {
	}

	public void testPutProfileBadEtag() throws IOException {
	}

	public void testPostProfileBadEtag() throws IOException {
	}

	public void testGetProfilesWithSince() throws IOException {
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		JsonArray profiles = client.getAgentProfiles(a, "2014-05-02T17:28:47.00Z");
		assertNotNull(profiles);
		assertTrue(profiles.size() >= 2);
	}

	public void testGetPerson() throws IOException {
		AgentClient client = new AgentClient(lrs_uri, username, password);
		Agent a = new Agent();
		a.setMbox(mbox);
		Person p = client.getPerson(a);
		assertNotNull(p);
		assertEquals(p.getMbox()[0], mbox);
	}
}
