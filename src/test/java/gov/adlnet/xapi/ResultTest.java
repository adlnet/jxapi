package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Result;
import gov.adlnet.xapi.model.Score;

public class ResultTest {
	private static final String IRI = "http://example.com/result/unit-testing";
	private static final String DURATION = "long";
	private static final String RESPONSE = "response";
	private Result result = null;

	@Before
	public void setUp() throws Exception {
		result = new Result();
		result.setCompletion(true);
		result.setDuration(DURATION);
		result.setResponse(RESPONSE);
		Score score = new Score();
		result.setScore(score);
		result.setSuccess(true);
		JsonObject extensions = new JsonObject();
		extensions.addProperty(IRI, "Result Extension");
		result.setExtensions(extensions);
	}

	@After
	public void tearDown() throws Exception {
		result = null;
	}

	@Test
	public void testGetScore() {
		Score actual = result.getScore();
		assertNotNull(actual);
	}

	@Test
	public void testSetScore() {
		Score score = new Score();
		result.setScore(score);
		Score actual = result.getScore();
		assertNotNull(actual);
	}

	@Test
	public void testIsSuccess() {
		Boolean expected = true;
		Boolean actual = result.isSuccess();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetSuccess() {
		Boolean expected = false;
		result.setSuccess(expected);
		Boolean actual = result.isSuccess();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testIsCompletion() {
		Boolean expected = true;
		Boolean actual = result.isCompletion();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetCompletion() {
		Boolean expected = false;
		result.setCompletion(expected);
		Boolean actual = result.isCompletion();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetResponse() {
		String expected = RESPONSE;
		String actual = result.getResponse();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetResponse() {
		String expected = "New " + RESPONSE;
		result.setResponse(expected);
		String actual = result.getResponse();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDuration() {
		String expected = DURATION;
		String actual = result.getDuration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetDuration() {
		String expected = "New " + DURATION;
		result.setDuration(expected);
		String actual = result.getDuration();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetExtensions() {
		JsonObject actual = result.getExtensions();
		assertNotNull(actual);
	}

	@Test
	public void testSetExtensions() {
		JsonObject expected = new JsonObject();
		expected.addProperty(IRI, "New Result Extension");
		result.setExtensions(expected);
		JsonObject actual = result.getExtensions();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() {
		String expected = IRI;
		JsonElement actual = result.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("extensions").toString().contains(expected));
		assert (obj.get("score").toString().contains("scaled"));
	}

}
