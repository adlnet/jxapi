package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.About;

public class AboutTest {

	private About about = null;

	@Before
	public void setUp() throws Exception {
		about = new About();
		ArrayList<String> versionInput = new ArrayList<String>();
		String expected = "1.0";
		versionInput.add(expected);
		about.setVersion(versionInput);
		JsonObject extension = new JsonObject();
		extension.addProperty("about", "About Extension");
		about.setExtensions(extension);
	}

	@After
	public void tearDown() throws Exception {
		about = null;
	}

	@Test
	public void testGetVersion() {
		String expected = "1.0";
		ArrayList<String> version = about.getVersion();
		assertNotNull(version);
		String actual = version.get(0);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetExtensions() {
		JsonObject expected = new JsonObject();
		expected.addProperty("about", "About Extension");
		JsonObject actual = about.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get("about"), actual.get("about"));
	}

	@Test
	public void testSetVersion() {
		ArrayList<String> versionInput = new ArrayList<String>();
		String expected = "1.0a";
		versionInput.add(expected);
		about.setVersion(versionInput);
		ArrayList<String> version = about.getVersion();
		assertNotNull(version);
		String actual = version.get(0);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetExtensions() {
		JsonObject expected = new JsonObject();
		expected.addProperty("id", UUID.randomUUID().toString());
		about.setExtensions(expected);
		JsonObject actual = about.getExtensions();
		assertNotNull(actual);
		assertEquals(expected.get("id"), actual.get("id"));
	}

}
