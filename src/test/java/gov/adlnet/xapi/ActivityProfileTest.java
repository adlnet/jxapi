package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.ActivityProfile;

public class ActivityProfileTest {

	private ActivityProfile activityProfile = null;

	@Before
	public void setUp() throws Exception {
		String activityId = "http://example.com/activities/unit-testing";
		String profileId = "Unit Testing 101";
		activityProfile = new ActivityProfile(activityId, profileId);
		assertNotNull(activityProfile);
		JsonObject p = new JsonObject();
		p.addProperty("ActPro", "Unit Test");
		activityProfile.setProfile(p);

	}

	@After
	public void tearDown() throws Exception {
		activityProfile = null;
	}

	@Test
	public void testActivityProfile() {
		ActivityProfile activityProfile = new ActivityProfile();
		assertNotNull(activityProfile);
	}

	@Test
	public void testActivityProfileStringString() {
		String activityId = "http://example.com/activities/unit-testing";
		String profileId = "Unit Testing 101";
		ActivityProfile activityProfile = new ActivityProfile(activityId, profileId);
		assertNotNull(activityProfile);
	}

	@Test
	public void testGetActivityId() {
		String expected = "http://example.com/activities/unit-testing";
		String actual = activityProfile.getActivityId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetActivityId() {
		String expected = "http://example.com/activities/unit-testing-new";
		activityProfile.setActivityId(expected);
		String actual = activityProfile.getActivityId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetProfileId() {
		String expected = "Unit Testing 101";
		String actual = activityProfile.getProfileId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetProfileId() {
		String expected = "Unit Testing 101 New";
		activityProfile.setProfileId(expected);
		String actual = activityProfile.getProfileId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetProfile() {
		JsonObject expected = new JsonObject();
		expected.addProperty("ActPro", "Unit Test");
		JsonObject actual = activityProfile.getProfile();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetProfile() {
		JsonObject expected = new JsonObject();
		expected.addProperty("ActPro", "Unit Test New");
		activityProfile.setProfile(expected);
		JsonObject actual = activityProfile.getProfile();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
