package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.util.AttachmentAndType;
import gov.adlnet.xapi.util.AttachmentResult;

public class AttachmentResultTest {

	private static final String MBOX = "mailto:test@example.com";
	private static final String VERB = "http://example.com/tested";
	private static final String HASH = "de29f036915c5c6546da6b16eabc6743b9e031244a015a0c99b664c3014f3163";
	private String ACTIVITY_ID;
	private static final String RESPONSE_MESSAGE = "--======ADL_LRS======" + "Content-Type:text/plain"
			+ "Content-Transfer-Encoding:binary"
			+ "X-Experience-API-Hash:de29f036915c5c6546da6b16eabc6743b9e031244a015a0c99b664c3014f3163"
			+ "This is a text/plain test." + " --======ADL_LRS======--";
	private Statement statement;
	private StatementResult statements;
	private byte[] attachment;
	private String type;
	private Map<String, AttachmentAndType> attachments;

	@Before
	public void setUp() throws Exception {
		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb(VERB);
		Activity act = new Activity(ACTIVITY_ID);
		statement = new Statement(a, v, act);

		ArrayList<Statement> statementList = new ArrayList<Statement>();
		statementList.add(statement);

		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		act = new Activity(ACTIVITY_ID);
		statement = new Statement(a, v, act);
		statementList.add(statement);

		statements = new StatementResult();
		statements.setStatements(statementList);

		type = "text/plain";
		attachment = "This is a text/plain test.".getBytes("UTF-8");

		AttachmentAndType att = new AttachmentAndType(attachment, type);
		attachments = new HashMap<String, AttachmentAndType>();
		attachments.put(HASH, att);
	}

	@After
	public void tearDown() throws Exception {
		statement = null;
		statements = null;
		attachments = null;
	}

	@Test
	public void testAttachmentResultStringStatementMapOfStringAttachmentAndType() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statement, attachments);
		assertNotNull(a);
	}

	@Test
	public void testAttachmentResultStringStatementResultMapOfStringAttachmentAndType() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
	}

	@Test
	public void testSetResponseMessage() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
		String responseMessage = "--======ADL_LRS======" + "Content-Type:application/json"
				+ "{\"verb\": {\"id\": \"http://example.com/tested\"}, \"version\": \"1.0.0\", "
				+ "\"timestamp\": \"2017-03-06T16:21:36.056551+00:00\", \"object\": "
				+ "{\"definition\": {\"correctResponsesPattern\": [\"http://example.com\"],"
				+ " \"moreInfo\": \"http://example.com\", \"interactionType\": \"choice\","
				+ " \"choices\": [{\"id\": \"http://example.com\", \"description\": {\"en-US\":"
				+ " \"test\"}}]}, \"id\": \"http://example.com/5e07aea2-b8b3-42ba-9e3a-fc76492f5cf4\","
				+ " \"objectType\": \"Activity\"}, \"actor\": {\"mbox\": \"mailto:test@example.com\","
				+ " \"objectType\": \"Agent\"}, \"stored\": \"2017-03-06T16:21:36.056551+00:00\","
				+ " \"authority\": {\"mbox\": \"mailto:jxapi@example.com\", \"name\": \"jXAPI\","
				+ " \"objectType\": \"Agent\"}, \"id\": \"9aaf0d77-a819-4bfc-9d9f-32f6f3458c3f\","
				+ " \"attachments\": [{\"sha2\": \"de29f036915c5c6546da6b16eabc6743b9e031244a015a0c99b664c3014f3163\","
				+ " \"contentType\": \"text/plain\", \"length\": 86, \"usageType\": \"http://example.com/test/usage\","
				+ " \"display\": {\"en-US\": \"jxapi Test Attachment From File\"}}]}";
		a.setResponseMessage(responseMessage);
		assertEquals(a.getResponseMessage(), responseMessage);
	}

	@Test
	public void testSetXapiStatement() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);

		Agent agent = new Agent();
		agent.setMbox(MBOX);
		Verb v = new Verb(VERB);
		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		Activity act = new Activity(ACTIVITY_ID);
		Statement inputStatement = new Statement(agent, v, act);
		a.setXapiStatement(inputStatement);
		assertEquals(inputStatement, a.getXapiStatement());
	}

	@Test
	public void testSetXapiStatements() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);

		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		Agent agent = new Agent();
		agent.setMbox(MBOX);
		Verb v = new Verb(VERB);
		Activity act = new Activity(ACTIVITY_ID);
		statement = new Statement(agent, v, act);

		ArrayList<Statement> statementList = new ArrayList<Statement>();
		statementList.add(statement);

		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();
		act = new Activity(ACTIVITY_ID);
		statement = new Statement(agent, v, act);
		statementList.add(statement);

		StatementResult inputStatements = new StatementResult();
		inputStatements.setStatements(statementList);
		a.setXapiStatements(inputStatements);
		ArrayList<Statement> actual = a.getXapiStatements().getStatements();
		assertEquals(2, actual.size());
	}

	@Test
	public void testSetAttachments() throws UnsupportedEncodingException {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);

		String testHash = "abc6743b9e031244a015";
		String type = "test/plain";
		byte[] attachment = "This is a new text/plain test.".getBytes("UTF-8");
		
		AttachmentAndType att = new AttachmentAndType(attachment, type);
		Map<String, AttachmentAndType> inputAttachments = new HashMap<String, AttachmentAndType>();
		inputAttachments.put(testHash, att);
		a.setAttachments(inputAttachments);
		assertEquals(attachment, a.getAttachment().get(testHash).getAttachment());
		assertEquals(type, a.getAttachment().get(testHash).getType());
	}

	@Test
	public void testGetResponseMessage() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
		assertEquals(a.getResponseMessage(), RESPONSE_MESSAGE);
	}

	@Test
	public void testGetXapiStatement() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
		a.getXapiStatement();
	}

	@Test
	public void testGetXapiStatements() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
		a.getXapiStatements();
	}

	@Test
	public void testGetAttachment() {
		AttachmentResult a = new AttachmentResult(RESPONSE_MESSAGE, statements, attachments);
		assertNotNull(a);
		assertEquals(Arrays.toString(attachment), Arrays.toString(a.getAttachment().get(HASH).getAttachment()));
		assertEquals(type, a.getAttachment().get(HASH).getType());
	}

}
