package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;

import gov.adlnet.xapi.model.Attachment;

public class AttachmentTest {
	private Attachment attachment = null;

	@Before
	public void setUp() throws Exception {
		attachment = new Attachment();
	}

	@After
	public void tearDown() throws Exception {
		attachment = null;
	}

	@Test
	public void testGetUsageType() throws URISyntaxException {
		URI expected = new URI("http://testUsageType.com");
		attachment.setUsageType(expected);
		URI actual = attachment.getUsageType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetUsageType() throws URISyntaxException {
		URI expected = new URI("http://testUsageType.com");
		attachment.setUsageType(expected);
		URI actual = attachment.getUsageType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDisplay() {
		String key = "en-US";
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put(key, "Test Display.");
		attachment.setDisplay(expected);
		HashMap<String, String> actual = attachment.getDisplay();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testSetDisplay() {
		String key = "en-US";
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put(key, "Test Display.");
		attachment.setDisplay(expected);
		HashMap<String, String> actual = attachment.getDisplay();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testGetDescription() {
		String key = "en-US";
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put(key, "Test description.");
		attachment.setDescription(expected);
		HashMap<String, String> actual = attachment.getDescription();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testSetDescription() {
		String key = "en-US";
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put(key, "Test description.");
		attachment.setDescription(expected);
		HashMap<String, String> actual = attachment.getDescription();
		assertNotNull(actual);
		assertEquals(expected.get(key), actual.get(key));
	}

	@Test
	public void testGetContentType() {
		String expected = "text/plain";
		attachment.setContentType(expected);
		String actual = attachment.getContentType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetContentType() {
		String expected = "text/plain";
		attachment.setContentType(expected);
		String actual = attachment.getContentType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetLength() {
		int expected = 10;
		attachment.setLength(expected);
		int actual = attachment.getLength();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetLength() {
		int expected = 10;
		attachment.setLength(expected);
		int actual = attachment.getLength();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSha2() {
		String expected = "sha2test";
		attachment.setSha2(expected);
		String actual = attachment.getSha2();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetSha2() {
		String expected = "sha2test";
		attachment.setSha2(expected);
		String actual = attachment.getSha2();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetFileUrl() throws URISyntaxException {
		URI expected = new URI("http://test.com");
		attachment.setFileUrl(expected);
		URI actual = attachment.getFileUrl();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetFileUrl() throws URISyntaxException {
		URI expected = new URI("http://test.com");
		attachment.setFileUrl(expected);
		URI actual = attachment.getFileUrl();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() throws URISyntaxException {
		String contentType = "text/plain";
		Attachment expected = new Attachment();
		expected.setContentType(contentType);
		expected.setLength(10);
		expected.setUsageType(new URI("http://test.com"));

		String key = "en-US";
		HashMap<String, String> description = new HashMap<String, String>();
		description.put(key, "Test description.");
		expected.setDescription(description);

		URI fileUrl = new URI("http://test.com");
		expected.setFileUrl(fileUrl);
		JsonElement actual = expected.serialize();

		assertNotNull(actual);
		assertEquals(expected.serialize().toString(), actual.toString());

		expected.setUsageType(null);
		try {
			expected.serialize();
		} catch (Exception e) {
			assert (true);
		}
	}

}
