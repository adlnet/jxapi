package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Attachment;
import gov.adlnet.xapi.model.Context;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.Result;
import gov.adlnet.xapi.model.SubStatement;
import gov.adlnet.xapi.model.Verb;

public class SubStatementTest {

	private static final String LANGUAGE = "en-US";
	private static final String TIME = "2017-04-18T12:17:00+00:00";
	private static final String MBOX = "mailto:test@example.com";
	private static final String TEST_IRI = "http://example.com" + UUID.randomUUID().toString();
	private static final String ID = "http://example.com/tested";
	private static final String EN_VERB = "tested";
	private SubStatement substatement = null;
	private Agent actor = null;
	private Verb verb = null;
	private Activity activity = null;
	private Result result = null;
	private Context context = null;
	private ArrayList<Attachment> attachments = null;

	@Before
	public void setUp() throws Exception {
		substatement = new SubStatement();
		substatement.setTimestamp(TIME);
		actor = new Agent();
		actor.setMbox(MBOX);
		HashMap<String, String> display = new HashMap<String, String>();
		display.put("en-US", EN_VERB);
		verb = new Verb(ID, display);
		activity = new Activity(TEST_IRI);
		substatement.setActor(actor);
		substatement.setVerb(verb);
		substatement.setObject(activity);
		result = new Result();
		result.setSuccess(false);
		substatement.setResult(result);
		context = new Context();
		context.setLanguage(LANGUAGE);
		substatement.setContext(context);
		attachments = new ArrayList<Attachment>();
		Attachment att = new Attachment();

		HashMap<String, String> attDisplay = new HashMap<String, String>();
		attDisplay.put("en-US", "Test Display.");
		att.setDisplay(attDisplay);

		HashMap<String, String> description = new HashMap<String, String>();
		description.put("en-US", "Test Description.");
		att.setDescription(description);

		URI usageType = new URI("http://example.com/test/usage");
		att.setUsageType(usageType);

		byte[] arr = "This is a text/plain test.".getBytes("UTF-8");
		String contentType = "text/plain";
//		att.setContentType(contentType);
//		att.setLength(arr.length);

		attachments.add(att);
		substatement.setAttachments(attachments);
	}

	@After
	public void tearDown() throws Exception {
		substatement = null;
		actor = null;
		verb = null;
		activity = null;
		result = null;
		context = null;
	}

	@Test
	public void testGetTimestamp() {
		String expected = TIME;
		String actual = substatement.getTimestamp();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetTimestamp() {
		String expected = "2017-05-18T12:17:00+00:00";
		substatement.setTimestamp(expected);
		String actual = substatement.getTimestamp();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAttachments() {
		ArrayList<Attachment> expected = attachments;
		ArrayList<Attachment> actual = substatement.getAttachments();
		assertNotNull(actual);
		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void testSetAttachments() throws UnsupportedEncodingException, URISyntaxException {
		ArrayList<Attachment> expected = attachments;
		expected.remove(0);

		Attachment att = new Attachment();

		HashMap<String, String> attDisplay = new HashMap<String, String>();
		attDisplay.put("en-US", "Test Display.");
		att.setDisplay(attDisplay);

		HashMap<String, String> description = new HashMap<String, String>();
		description.put("en-US", "New Test Description.");
		att.setDescription(description);

		URI usageType = new URI("http://example.com/test/usage");
		att.setUsageType(usageType);

		byte[] arr = "This is a new text/plain test.".getBytes("UTF-8");
		String contentType = "text/plain";
//		att.setContentType(contentType);
//		att.setLength(arr.length);

		expected.add(att);

		ArrayList<Attachment> actual = substatement.getAttachments();
		assertNotNull(actual);
		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void testGetContext() {
		Context expected = context;
		Context actual = substatement.getContext();
		assertNotNull(actual);
		assertEquals(expected.getLanguage(), actual.getLanguage());
	}

	@Test
	public void testSetContext() {
		Context expected = context;
		expected.setLanguage("es");
		substatement.setContext(expected);
		Context actual = substatement.getContext();
		assertNotNull(actual);
		assertEquals(expected.getLanguage(), actual.getLanguage());
	}

	@Test
	public void testGetVerb() {
		Verb expected = verb;
		Verb actual = substatement.getVerb();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDisplay(), actual.getDisplay());
	}

	@Test
	public void testSetVerb() {
		Verb expected = new Verb(ID);
		substatement.setVerb(expected);
		Verb actual = substatement.getVerb();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void testGetActor() {
		Actor expected = actor;
		Actor actual = substatement.getActor();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}

	@Test
	public void testSetActor() {
		Actor expected = new Agent();
		expected.setMbox("mailto:test@example.com");
		substatement.setActor(expected);
		Actor actual = substatement.getActor();
		assertNotNull(actual);
		assertEquals(expected.getMbox(), actual.getMbox());
	}

	@Test
	public void testGetObject() {
		Activity expected = activity;
		IStatementObject actual = substatement.getObject();
		assertNotNull(actual);
		assertEquals(expected.getObjectType(), actual.getObjectType());
	}

	@Test
	public void testSetObject() {
		Activity expected = new Activity("http://example.com" + UUID.randomUUID().toString());
		substatement.setObject(expected);
		IStatementObject actual = substatement.getObject();
		assertNotNull(actual);
		assertEquals(expected.getObjectType(), actual.getObjectType());

		try {
			substatement.setObject(substatement);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void testGetResult() {
		Result expected = result;
		Result actual = substatement.getResult();
		assertNotNull(actual);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testSetResult() {
		Result expected = result;
		expected.setSuccess(true);
		substatement.setResult(expected);
		Result actual = substatement.getResult();
		assertNotNull(actual);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testGetObjectType() {
		String expected = "SubStatement";
		String actual = substatement.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() throws UnsupportedEncodingException, URISyntaxException {
		SubStatement expected = new SubStatement();
		expected.setActor(actor);
		expected.setVerb(verb);
		expected.setObject(activity);

		Attachment attachment = new Attachment();
		byte[] arr = "This is a text/plain test.".getBytes("UTF-8");
		String contentType = "text/plain";
//		attachment.setContentType(contentType);
//		attachment.setLength(arr.length);
		attachment.setUsageType(new URI("http://test.com"));

		ArrayList<Attachment> expectedList = new ArrayList<Attachment>();
		expectedList.add(attachment);
		expected.setAttachments(expectedList);

		context.setLanguage("en-US");
		expected.setContext(context);

		result.setSuccess(true);
		expected.setResult(result);

		expected.setTimestamp(TIME);

		JsonElement actual = substatement.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assertEquals(obj.getAsJsonPrimitive("timestamp").getAsString(), expected.getTimestamp());
		assertEquals(obj.getAsJsonObject("actor").getAsJsonPrimitive("mbox").getAsString(), expected.getActor().getMbox());
	}

}
