package gov.adlnet.xapi;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.ActivityState;
import gov.adlnet.xapi.model.Agent;

public class ActivityStateTest {

	private ActivityState activityState = null;
	private static final UUID REG_ID = UUID.randomUUID();

	@Before
	public void setUp() throws Exception {
		String activityId = "http://example.com/activity-state/unit-testing";
		String stateId ="state"+1234;
		Agent agent = new Agent();
		agent.setMbox("mailto:test@example.com");
	    activityState = new ActivityState(activityId, stateId, agent);
		assertNotNull(activityState);
		activityState.setRegistration(REG_ID);
		JsonObject state = new JsonObject();
		state.addProperty("ActState", "Unit Test");
		activityState.setState(state);
	}

	@After
	public void tearDown() throws Exception {
		activityState = null;
	}

	@Test
	public void testActivityState() {
		ActivityState activityState = new ActivityState();
		assertNotNull(activityState);
	}

	@Test
	public void testActivityStateStringStringAgent() {
		String activityId = "http://example.com/activity-state/unit-testing";
		String stateId ="state"+1234;
		Agent agent = new Agent();
		agent.setMbox("mailto:test@example.com");
		ActivityState activityState = new ActivityState(activityId, stateId, agent);
		assertNotNull(activityState);
	}

	@Test
	public void testGetActivityId() {
		String expected = "http://example.com/activity-state/unit-testing";
		String actual = activityState.getActivityId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetActivityId() {
		String expected = "http://example.com/activity-state/unit-testing-new";
		activityState.setActivityId(expected);
		String actual = activityState.getActivityId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetStateId() {
		String expected = "state"+1234;
		String actual = activityState.getStateId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetStateId() {
		String expected = "state"+123456;
		activityState.setStateId(expected);
		String actual = activityState.getStateId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetAgent() {
		Agent expected = new Agent();
		expected.setMbox("mailto:new@example.com");
		activityState.setAgent(expected);
		Agent actual = activityState.getAgent();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}
	

	@Test
	public void testGetAgent() {
		Agent expected = new Agent();
		expected.setMbox("mailto:test@example.com");
		Agent actual = activityState.getAgent();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}

	@Test
	public void testGetRegistration() {
		UUID expected = REG_ID;
		UUID actual = activityState.getRegistration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetRegistration() {
		UUID expected = UUID.randomUUID();
		activityState.setRegistration(expected);
		UUID actual = activityState.getRegistration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetState() {
		JsonObject expected = new JsonObject();
		expected.addProperty("ActState", "Unit Test");
		JsonObject actual = activityState.getState();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetState() {
		JsonObject expected = new JsonObject();
		expected.addProperty("ActState", "Unit Test New");
		activityState.setState(expected);
		JsonObject actual = activityState.getState();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
