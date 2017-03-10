package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	private static final String HASH = "e9603d6feb592d5c214c9dfacc6d6d73874755a01af6b59559c63b40e10c7dfe";
	private String ACTIVITY_ID;
	private String responseMessage;
	private Statement statement;
	private StatementResult statements;
	private byte[] bytes;
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
		
		responseMessage = "--======ADL_LRS======"
				+ "Content-Type:text/plain"
				+ "Content-Transfer-Encoding:binary"
				+ "X-Experience-API-Hash:e9603d6feb592d5c214c9dfacc6d6d73874755a01af6b59559c63b40e10c7dfe"
				+ "[84, 104, 105, 115, 32, 105, 115, 32, 116, 104, 101, 32, 102, 105,"
				+ "114, 115, 116, 32, 108, 105, 110, 101, 10, 84, 104, 105, 115, 32, 105,"
				+ "115, 32, 116, 104, 101, 32, 115, 101, 99, 111, 110, 100, 32, 108, 105,"
				+ "110, 101, 33, 33, 33, 10, 56, 57, 56, 55, 97, 55, 49, 97, 45, 51, 56,"
				+ "100, 57, 45, 52, 52, 55, 54, 45, 56, 53, 57, 99, 45, 51, 55, 57, 48, 52,"
				+ "97, 100, 50, 48, 51, 51, 48]"
				+" --======ADL_LRS======--";

		type = "text/plain";
		bytes = new byte[] { 84, 104, 105, 115, 32, 105, 115, 32, 116, 104, 101, 32, 102, 105, 114, 115, 116, 32, 108,
				105, 110, 101, 10, 84, 104, 105, 115, 32, 105, 115, 32, 116, 104, 101, 32, 115, 101, 99, 111, 110, 100,
				32, 108, 105, 110, 101, 33, 33, 33, 10, 53, 57, 56, 52, 57, 52, 98, 99, 45, 52, 48, 49, 102, 45, 52, 57,
				102, 53, 45, 98, 50, 102, 101, 45, 97, 49, 98, 99, 53, 48, 102, 50, 100, 50, 100, 49 };
		ArrayList<byte[]> attachment = new ArrayList<byte[]>();
		attachment.add(bytes);
		AttachmentAndType att = new AttachmentAndType(attachment, type);
		attachments = new HashMap<String, AttachmentAndType>();
		attachments.put("e9603d6feb592d5c214c9dfacc6d6d73874755a01af6b59559c63b40e10c7dfe", att);
		
	}

	@After
	public void tearDown() throws Exception {
		statement = null;
		statements = null;
		attachments = null;		
	}

	@Test
	public void testAttachmentResultStringStatementMapOfStringAttachmentAndType() {
		AttachmentResult a = new AttachmentResult(responseMessage, statement, attachments);
		assertNotNull(a);
	}

	@Test
	public void testAttachmentResultStringStatementResultMapOfStringAttachmentAndType() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
	}

	@Test
	public void testSetResponseMessage() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		String responseMessage = "--======ADL_LRS======"
				+ "Content-Type:application/json"
				+ "{\"verb\": {\"id\": \"http://example.com/tested\"}, \"version\": \"1.0.0\", "
				+ "\"timestamp\": \"2017-03-06T16:21:36.056551+00:00\", \"object\": "
				+ "{\"definition\": {\"correctResponsesPattern\": [\"http://example.com\"],"
				+ " \"moreInfo\": \"http://example.com\", \"interactionType\": \"choice\","
				+ " \"choices\": [{\"id\": \"http://example.com\", \"description\": {\"en-US\":"
				+ " \"test\"}}]}, \"id\": \"http://example.com/e6d2c93a-5913-4495-9881-b41b8856a407\","
				+ " \"objectType\": \"Activity\"}, \"actor\": {\"mbox\": \"mailto:test@example.com\","
				+ " \"objectType\": \"Agent\"}, \"stored\": \"2017-03-06T16:21:36.056551+00:00\","
				+ " \"authority\": {\"mbox\": \"mailto:jxapi@example.com\", \"name\": \"jXAPI\","
				+ " \"objectType\": \"Agent\"}, \"id\": \"e8e7f9d7-7ab3-4e68-b73f-da73353f9d03\","
				+ " \"attachments\": [{\"sha2\": \"e9603d6feb592d5c214c9dfacc6d6d73874755a01af6b59559c63b40e10c7dfe\","
				+ " \"contentType\": \"text/plain\", \"length\": 86, \"usageType\": \"http://example.com/test/usage\","
				+ " \"display\": {\"en-US\": \"jxapi Test Attachment From File\"}}]}";
		a.setResponseMessage(responseMessage);
		assertEquals(a.getResponseMessage(), responseMessage);
	}

	@Test
	public void testSetXapiStatement() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
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
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
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
	public void testSetAttachments() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		
		String testHash = "e9603d6feb592d5";
		String type = "test/plain";
		byte[] bytes = new byte[] { 84, 104, 105, 115, 32, 105 };
		ArrayList<byte[]> attachment = new ArrayList<byte[]>();
		attachment.add(bytes);
		AttachmentAndType att = new AttachmentAndType(attachment, type);
		Map<String, AttachmentAndType>inputAttachments = new HashMap<String, AttachmentAndType>();
		inputAttachments.put(testHash, att);
		a.setAttachments(inputAttachments);
		assertEquals(attachment, a.getAttachment().get(testHash).getAttachment());
		assertEquals(type, a.getAttachment().get(testHash).getType());
	}

	@Test
	public void testGetResponseMessage() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		assertEquals(a.getResponseMessage(), responseMessage);
	}

	@Test
	public void testGetXapiStatement() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		a.getXapiStatement();
	}

	@Test
	public void testGetXapiStatements() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		a.getXapiStatements();
	}

	@Test
	public void testGetAttachment() {
		AttachmentResult a = new AttachmentResult(responseMessage, statements, attachments);
		assertNotNull(a);
		assertEquals(Arrays.toString(bytes), Arrays.toString(a.getAttachment().get(HASH).getAttachment().get(0)));
		assertEquals(type, a.getAttachment().get(HASH).getType());
	}

}
