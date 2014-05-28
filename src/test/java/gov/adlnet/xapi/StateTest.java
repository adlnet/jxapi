package gov.adlnet.xapi;

import gov.adlnet.xapi.client.StateClient;
import gov.adlnet.xapi.model.Agent;

import java.io.IOException;

import junit.framework.TestCase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
