package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ContextActivities;

public class ContentActivitiesTest {

	private static final String ID = UUID.randomUUID().toString();
	private ContextActivities contextActivities = null;
	private ArrayList<Activity> category = null;
	private ArrayList<Activity> grouping = null;
	private ArrayList<Activity> other = null;
	private ArrayList<Activity> parent = null;

	@Before
	public void setUp() throws Exception {
		category = new ArrayList<Activity>();
		category.add(new Activity(ID));

		grouping = new ArrayList<Activity>();
		grouping.add(new Activity(ID));

		other = new ArrayList<Activity>();
		other.add(new Activity(ID));

		parent = new ArrayList<Activity>();
		parent.add(new Activity(ID));

		contextActivities = new ContextActivities();
		contextActivities.setCategory(category);
		contextActivities.setGrouping(grouping);
		contextActivities.setOther(other);
		contextActivities.setParent(parent);
	}

	@After
	public void tearDown() throws Exception {
		contextActivities = null;
		category = null;
		grouping = null;
		other = null;
		parent = null;
	}

	@Test
	public void testGetParent() {
		ArrayList<Activity> expected = parent;
		ArrayList<Activity> actual = contextActivities.getParent();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testSetParent() {
		String newID = "New." + ID;
		ArrayList<Activity> expected = parent;
		expected.add(new Activity(newID));
		contextActivities.setParent(expected);
		ArrayList<Activity> actual = contextActivities.getParent();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testGetGrouping() {
		ArrayList<Activity> expected = grouping;
		ArrayList<Activity> actual = contextActivities.getGrouping();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testSetGrouping() {
		String newID = "New." + ID;
		ArrayList<Activity> expected = grouping;
		expected.add(new Activity(newID));
		contextActivities.setGrouping(expected);
		ArrayList<Activity> actual = contextActivities.getGrouping();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testGetCategory() {
		ArrayList<Activity> expected = category;
		ArrayList<Activity> actual = contextActivities.getCategory();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testSetCategory() {
		String newID = "New." + ID;
		ArrayList<Activity> expected = category;
		expected.add(new Activity(newID));
		contextActivities.setCategory(expected);
		ArrayList<Activity> actual = contextActivities.getCategory();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testGetOther() {
		ArrayList<Activity> expected = other;
		ArrayList<Activity> actual = contextActivities.getOther();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testSetOther() {
		String newID = "New." + ID;
		ArrayList<Activity> expected = other;
		expected.add(new Activity(newID));
		contextActivities.setOther(expected);
		ArrayList<Activity> actual = contextActivities.getOther();
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(ID, actual.get(0).getId());
	}

	@Test
	public void testSerialize() {
		String expected = ID;
		JsonElement actual = contextActivities.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("parent").toString().contains(expected));
	}

}
