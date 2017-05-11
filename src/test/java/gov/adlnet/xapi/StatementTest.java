package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import gov.adlnet.xapi.model.Result;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.Verb;

public class StatementTest {
	private static final String ID = UUID.randomUUID().toString();
	private static final String MBOX = "mailto:test@example.com";
	private static final String TEST_IRI = "http://example.com";
	private Statement statement = null;
	private Agent agent = null;

	@Before
	public void setUp() throws Exception {
		agent = new Agent();
		agent.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(TEST_IRI + UUID.randomUUID().toString());
		statement = new Statement(agent, v, act);
		statement.setId(ID);
		
		Attachment attachment = new Attachment();
		Attachment expected = new Attachment();
		String att = "This is a text/plain test.";
		String contentType = "text/plain";
		expected.addAttachment(att, contentType);
		attachment.setUsageType(new URI("http://test.com"));

		ArrayList<Attachment> expectedList = new ArrayList<Attachment>();
		expectedList.add(attachment);
		statement.setAttachments(expectedList);
		
		Context context = new Context();
		context.setLanguage("en-US");
		statement.setContext(context);

		Result result = new Result();
		result.setSuccess(true);
		statement.setResult(result);
		
		String time = String.valueOf(System.currentTimeMillis());
		statement.setStored(time);
		statement.setTimestamp(time);
		statement.setAuthority(agent);
		statement.setVersion("1.0");
	}

	@After
	public void tearDown() throws Exception {
		statement = null;
		agent = null;
	}

	@Test
	public void testStatement() {
		Statement actual = new Statement();
		assertNotNull(actual);
	}

	@Test
	public void testStatementActorVerbIStatementObject() {
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/test");
		Activity act = new Activity(TEST_IRI + UUID.randomUUID().toString());
		Statement actual = new Statement(a, v, act);
		assertNotNull(actual);
	}

	@Test
	public void testGetTimestamp() {
		String expected = String.valueOf(System.currentTimeMillis());
		statement.setTimestamp(expected);
		String actual = statement.getTimestamp();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetTimestamp() {
		String expected = String.valueOf(System.currentTimeMillis());
		statement.setTimestamp(expected);
		String actual = statement.getTimestamp();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetStored() {
		String expected = String.valueOf(System.currentTimeMillis());
		statement.setStored(expected);
		String actual = statement.getStored();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetStored() {
		String expected = String.valueOf(System.currentTimeMillis());
		statement.setStored(expected);
		String actual = statement.getStored();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAuthority() {
		statement.setAuthority(agent);
		Actor actual = statement.getAuthority();
		assertNotNull(actual);
		assertEquals(agent, actual);
	}

	@Test
	public void testSetAuthority() {
		statement.setAuthority(agent);
		Actor actual = statement.getAuthority();
		assertNotNull(actual);
		assertEquals(agent, actual);
	}

	@Test
	public void testGetVersion() {
		String expected = "1.0";
		statement.setVersion(expected);
		String actual = statement.getVersion();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetVersion() {
		String expected = "1.0";
		statement.setVersion(expected);
		String actual = statement.getVersion();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAttachments() throws IOException, NoSuchAlgorithmException {
		Attachment expected = new Attachment();
		String att = "This is a text/plain test.";
		String contentType = "text/plain";
		expected.addAttachment(att, contentType);

		ArrayList<Attachment> expectedList = new ArrayList<Attachment>();
		expectedList.add(expected);
		statement.setAttachments(expectedList);
		ArrayList<Attachment> actualList = statement.getAttachments();
		Attachment actual = actualList.get(0);
		assertNotNull(actual);
		assertEquals(expected.getContentType(), actual.getContentType());
		assertEquals(expected.getLength(), actual.getLength());
	}

	@Test
	public void testSetAttachments()  throws IOException, NoSuchAlgorithmException  {
		Attachment expected = new Attachment();
		String att = "This is a text/plain test.";
		String contentType = "text/plain";
		expected.addAttachment(att, contentType);

		ArrayList<Attachment> expectedList = new ArrayList<Attachment>();
		expectedList.add(expected);
		statement.setAttachments(expectedList);
		ArrayList<Attachment> actualList = statement.getAttachments();
		Attachment actual = actualList.get(0);
		assertNotNull(actual);
		assertEquals(expected.getContentType(), actual.getContentType());
		assertEquals(expected.getLength(), actual.getLength());
	}

	@Test
	public void testGetContext() {
		Context expected = new Context();
		expected.setLanguage("English");
		statement.setContext(expected);
		Context actual = statement.getContext();
		assertNotNull(actual);
		assertEquals(expected.getLanguage(), actual.getLanguage());
	}

	@Test
	public void testSetContext() {
		Context expected = new Context();
		expected.setLanguage("English");
		statement.setContext(expected);
		Context actual = statement.getContext();
		assertNotNull(actual);
		assertEquals(expected.getLanguage(), actual.getLanguage());
	}

	@Test
	public void testGetId() {
		String expected = ID;
		statement.setId(expected);
		String actual = statement.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetId() {
		String expected = UUID.randomUUID().toString();
		statement.setId(expected);
		String actual = statement.getId();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetVerb() {
		Verb expected = new Verb("http://example.com/tested");
		statement.setVerb(expected);
		Verb actual = statement.getVerb();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetVerb() {
		Verb expected = new Verb("http://example.com/tested");
		statement.setVerb(expected);
		Verb actual = statement.getVerb();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetActor() {
		Actor expected = agent;
		statement.setActor(expected);
		Actor actual = statement.getActor();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetActor() {
		Actor expected = agent;
		statement.setActor(expected);
		Actor actual = statement.getActor();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetObject() {
		Activity expected = new Activity(TEST_IRI + UUID.randomUUID().toString());
		statement.setObject(expected);
		Activity actual = (Activity) statement.getObject();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void testSetObject() {
		Activity expected = new Activity(TEST_IRI + UUID.randomUUID().toString());
		statement.setObject(expected);
		Activity actual = (Activity) statement.getObject();
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void testGetResult() {
		Result expected = new Result();
		expected.setSuccess(true);
		statement.setResult(expected);
		Result actual = statement.getResult();
		assertNotNull(actual);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testSetResult() {
		Result expected = new Result();
		expected.setSuccess(true);
		statement.setResult(expected);
		Result actual = statement.getResult();
		assertNotNull(actual);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testToString() {
		String verb = "http://example.com/tested";
		String activity = TEST_IRI + UUID.randomUUID().toString();
		Verb v = new Verb(verb);
		Activity act = new Activity(activity);
		Statement actual = new Statement(agent, v, act);
		assertNotNull(actual);
		assert(actual.toString().contains(agent.getMbox()));
		assert(actual.toString().contains(verb));
		assert(actual.toString().contains(activity));
	}

	@Test
	public void testSerialize() throws IOException, URISyntaxException {

		JsonElement actual = statement.serialize();
		assertNotNull(actual);
		assert(actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assertEquals(obj.getAsJsonPrimitive("id").getAsString(), ID);
	}

}
