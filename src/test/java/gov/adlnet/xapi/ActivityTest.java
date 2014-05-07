package gov.adlnet.xapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.client.ActivityClient;
import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ActivityTest extends TestCase {
	private static final String PROFILE_ID = UUID.randomUUID().toString();
	private static final String ACTIVITY_ID = "http://example.com";
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
	private static final String PASSWORD = "password";

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

	public void testGetActivity() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		Activity a = _client.getActivity(ACTIVITY_ID);
		assertNotNull(a);
		assertEquals(a.getId(), ACTIVITY_ID);
	}

	public void testSaveActivityProfile() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonObject obj = new JsonObject();
		assertTrue(_client.publishActivityProfile(ACTIVITY_ID, PROFILE_ID, obj));		
	}
	public void testGetActivities() throws IOException{
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonArray a = _client.getActivities(ACTIVITY_ID, null);
		assertNotNull(a);		
	}
	public void testGetActivitesWithSince() throws IOException {
		ActivityClient _client = new ActivityClient(LRS_URI, USERNAME, PASSWORD);
		JsonArray a = _client.getActivities(ACTIVITY_ID, "2014-05-02T17:28:47.00Z");
		assertNotNull(a);		
	}
}
