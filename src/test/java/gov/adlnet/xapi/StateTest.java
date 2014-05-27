package gov.adlnet.xapi;

import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.client.StateClient;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.State;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatementObjectAdapter;
import junit.framework.TestCase;

public class StateTest extends TestCase {
	private static final String STATE_ID = "questionState";
	private static final String ACTIVITY_ID = "http://example.com";
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
	private static final String PASSWORD = "password";

	public StateTest(String testName) throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException {
		super(testName);
	}

	public void testPublishState() throws IOException {
		StateClient client = new StateClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox("mailto:test@example.com");
		JsonObject obj = new JsonObject();
		obj.addProperty("test", "test");
		assertTrue(client.publishState(ACTIVITY_ID, a,
				STATE_ID, obj));
	}

	public void testGetState() throws IOException {
		StateClient client = new StateClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox("mailto:test@example.com");
		assertNotNull(client.getState(ACTIVITY_ID, a, STATE_ID));
	}
	public void testGetStates() throws IOException {
		StateClient client = new StateClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox("mailto:test@example.com");
		JsonArray stuff = client.getStates(ACTIVITY_ID, a, null, null);
		assertNotNull(stuff);
	}
}
