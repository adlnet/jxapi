package gov.adlnet.xapi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

import javax.mail.MessagingException;

import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.gson.JsonSyntaxException;

import gov.adlnet.xapi.AppTest.ISO8601;
import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Attachment;
import gov.adlnet.xapi.model.Context;
import gov.adlnet.xapi.model.ContextActivities;
import gov.adlnet.xapi.model.InteractionComponent;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementReference;
import gov.adlnet.xapi.model.StatementResult;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.Verbs;
import gov.adlnet.xapi.util.AttachmentResult;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class StatementClientTest extends TestCase {
	private String STMNT_ID;
	private String ACTIVITY_ID;
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";
	private static final String MBOX = "mailto:test@example.com";

	@Before
	public void setUp() throws IOException {
		ACTIVITY_ID = "http://example.com/" + UUID.randomUUID().toString();

		Agent a = new Agent();
		a.setMbox(MBOX);
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		STMNT_ID = st.getId();
		assertTrue(STMNT_ID.length() > 0);
		sc.putStatement(st, STMNT_ID);

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testStatementClientStringStringString() throws IOException {

		// Happy path
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Incorrect password
		sc = new StatementClient(LRS_URI, USERNAME, "passw0rd");
		try {
			sc.postStatement(st);
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			sc = new StatementClient("fail", USERNAME, PASSWORD);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testStatementClientURLStringString() throws IOException {
		URL lrs_url = new URL(LRS_URI);

		// Happy path
		StatementClient sc = new StatementClient(lrs_url, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Incorrect password
		sc = new StatementClient(lrs_url, USERNAME, "passw0rd");
		try {
			sc.postStatement(st);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testStatementClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		// Happy path
		StatementClient sc = new StatementClient(LRS_URI, encodedCreds);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		sc = new StatementClient(LRS_URI, USERNAME, "passw0rd");
		try {
			sc.postStatement(st);
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			sc = new StatementClient("fail", encodedCreds);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testStatementClientURLString() throws IOException {
		URL lrs_url = new URL(LRS_URI);
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		// Happy path
		StatementClient sc = new StatementClient(lrs_url, encodedCreds);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		sc.postStatement(st);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		sc = new StatementClient(lrs_url, encodedCreds);
		try {
			sc.postStatement(st);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testPostStatement() throws IOException {
		Agent a = new Agent();
		a.setMbox(MBOX);
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		String publishedId = sc.postStatement(st);
		assertTrue(publishedId.length() > 0);
	}

	@Test
	public void testPutStatement() throws IOException {
		Agent a = new Agent();
		a.setMbox(MBOX);
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		String stmntID = st.getId();
		assertTrue(stmntID.length() > 0);
		assertTrue(sc.putStatement(st, stmntID));

	}

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	private String generateSha2(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytes);
		return new String(Hex.encode(md.digest()));
	}

	@Test
	public void testPostStatementWithAttachment() throws IOException, URISyntaxException, NoSuchAlgorithmException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement statement = new Statement(a, v, act);

		ActivityDefinition ad = new ActivityDefinition();
		ad.setChoices(new ArrayList<InteractionComponent>());
		InteractionComponent ic = new InteractionComponent();
		ic.setId("http://example.com");
		ic.setDescription(new HashMap<String, String>());
		ic.getDescription().put("en-US", "test");
		ad.getChoices().add(ic);
		ad.setInteractionType("choice");

		ArrayList<String> crp = new ArrayList<String>();
		crp.add("http://example.com");
		ad.setCorrectResponsesPattern(crp);
		ad.setMoreInfo("http://example.com");
		act.setDefinition(ad);

		Attachment att = new Attachment();
		HashMap<String, String> attDis = new HashMap<String, String>();
		attDis.put("en-US", "jxapi Test Attachment From File");
		att.setDisplay(attDis);
		URI usageType = new URI("http://example.com/test/usage");
		att.setUsageType(usageType);

		File testFile = testFolder.newFile("testatt.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(testFile));
		out.write("This is the first line\n");
		out.write("This is the second line!!!\n");
		out.write(UUID.randomUUID().toString());
		out.close();

		String contentType = "text/plain";
		att.setContentType(contentType);
		att.setLength((int) testFile.length());
		byte[] arr = Files.readAllBytes(testFile.toPath());
		att.setSha2(generateSha2(arr));

		ArrayList<Attachment> attList = new ArrayList<Attachment>();
		attList.add(att);
		statement.setAttachments(attList);

		ArrayList<byte[]> realAtts = new ArrayList<byte[]>();
		realAtts.add(arr);

		String publishedId = sc.postStatementWithAttachment(statement, contentType, realAtts);

		assertTrue(publishedId.length() > 0);
		testFile.deleteOnExit();
	}

	@Test
	public void testGetStatementsString() throws IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		StatementResult collection = _client.getStatements();
		assertFalse(collection.getStatements().isEmpty());

		String more = collection.getMore();

		if (!more.isEmpty()) {
			StatementResult moreCollection = _client.getStatements(more);
			assertFalse(moreCollection.getStatements().isEmpty());
		}
	}

	@Test
	public void testGetStatements() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		StatementResult returned = sc.getStatements();
		assertNotNull(returned);
	}

	@Test
	public void testGetStatementsWithAttachments() throws NoSuchAlgorithmException, IOException, URISyntaxException, JsonSyntaxException, NumberFormatException, MessagingException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement statement = new Statement(a, v, act);

		ActivityDefinition ad = new ActivityDefinition();
		ad.setChoices(new ArrayList<InteractionComponent>());
		InteractionComponent ic = new InteractionComponent();
		ic.setId("http://example.com");
		ic.setDescription(new HashMap<String, String>());
		ic.getDescription().put("en-US", "test");
		ad.getChoices().add(ic);
		ad.setInteractionType("choice");

		ArrayList<String> crp = new ArrayList<String>();
		crp.add("http://example.com");
		ad.setCorrectResponsesPattern(crp);
		ad.setMoreInfo("http://example.com");
		act.setDefinition(ad);

		Attachment att = new Attachment();
		HashMap<String, String> attDis = new HashMap<String, String>();
		attDis.put("en-US", "jxapi Test Attachment From File");
		att.setDisplay(attDis);
		URI usageType = new URI("http://example.com/test/usage");
		att.setUsageType(usageType);

		File testFile = testFolder.newFile("testatt.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(testFile));
		out.write("This is the first line\n");
		out.write("This is the second line!!!\n");
		out.write(UUID.randomUUID().toString());
		out.close();

		String contentType = "text/plain";
		att.setContentType(contentType);
		att.setLength((int) testFile.length());
		byte[] arr = Files.readAllBytes(testFile.toPath());
		att.setSha2(generateSha2(arr));		
		
		ArrayList<Attachment> attList = new ArrayList<Attachment>();
		attList.add(att);
		statement.setAttachments(attList);

		ArrayList<byte[]> realAtts = new ArrayList<byte[]>();
		realAtts.add(arr);
 
		String publishedId = sc.postStatementWithAttachment(statement, contentType, realAtts);

		assertTrue(publishedId.length() > 0);
		AttachmentResult attachmntResult = sc.getStatementsWithAttachments();
		Statement s = attachmntResult.getXapiStatements().getStatements().get(0);
		assertTrue(s.getAttachments().get(0).getSha2().contains(att.getSha2()));
		
		attachmntResult = sc.addFilter("statementId", publishedId).getStatementsWithAttachments();
		String id = attachmntResult.getXapiStatement().getId();
		assertTrue(id.contains((publishedId)));

		testFile.deleteOnExit();		
	}
	
	@Test
	public void testGetStatementsWithAttachmentsString() throws NoSuchAlgorithmException, IOException, URISyntaxException, JsonSyntaxException, NumberFormatException, MessagingException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement statement = new Statement(a, v, act);

		ActivityDefinition ad = new ActivityDefinition();
		ad.setChoices(new ArrayList<InteractionComponent>());
		InteractionComponent ic = new InteractionComponent();
		ic.setId("http://example.com");
		ic.setDescription(new HashMap<String, String>());
		ic.getDescription().put("en-US", "test");
		ad.getChoices().add(ic);
		ad.setInteractionType("choice");

		ArrayList<String> crp = new ArrayList<String>();
		crp.add("http://example.com");
		ad.setCorrectResponsesPattern(crp);
		ad.setMoreInfo("http://example.com");
		act.setDefinition(ad);

		Attachment att = new Attachment();
		HashMap<String, String> attDis = new HashMap<String, String>();
		attDis.put("en-US", "jxapi Test Attachment From File");
		att.setDisplay(attDis);
		URI usageType = new URI("http://example.com/test/usage");
		att.setUsageType(usageType);

		File testFile = testFolder.newFile("testatt.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(testFile));
		out.write("This is the first line\n");
		out.write("This is the second line!!!\n");
		out.write(UUID.randomUUID().toString());
		out.close();

		String contentType = "text/plain";
		att.setContentType(contentType);
		att.setLength((int) testFile.length());
		byte[] arr = Files.readAllBytes(testFile.toPath());
		att.setSha2(generateSha2(arr));		
		
		ArrayList<Attachment> attList = new ArrayList<Attachment>();
		attList.add(att);
		statement.setAttachments(attList);

		ArrayList<byte[]> realAtts = new ArrayList<byte[]>();
		realAtts.add(arr);
 
		String publishedId = sc.postStatementWithAttachment(statement, contentType, realAtts);

		assertTrue(publishedId.length() > 0);

		AttachmentResult attachmntResult = sc.getStatementWithAttachments(publishedId);
		String id = attachmntResult.getXapiStatement().getId();
		assertTrue(id.contains((publishedId)));

		testFile.deleteOnExit();		
	}

	@Test
	public void testGetStatement() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Statement result = sc.getStatement(STMNT_ID);
		assertNotNull(result);
	}

	@Test
	public void testGetVoided() throws IOException {
		String voidedId = UUID.randomUUID().toString();
		Agent a = new Agent();
		a.setMbox(MBOX);
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement st = new Statement(a, v, act);
		st.setId(voidedId);

		Boolean put = sc.putStatement(st, voidedId);
		assertTrue(put);

		Statement stmt2 = new Statement(a, Verbs.voided(), new StatementReference(voidedId));
		String postedId = sc.postStatement(stmt2);
		assertTrue(postedId.length() > 0);

		Statement collection = sc.getVoided(voidedId);
		assertTrue(collection.getId().equals(voidedId));
	}

	@Test
	public void testAddFilter() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		String verbId = "http://example.com/tested";
		StatementResult result = sc.addFilter("verb", verbId).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testFilterByVerbVerb() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Verb v = new Verb("http://example.com/tested");
		StatementResult result = sc.filterByVerb(v).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getVerb());
			assertTrue(s.getVerb().getId().equals(v.getId()) || s.getVerb().getId().equals(Verbs.voided().getId()));
		}
	}

	@Test
	public void testFilterByVerbString() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		String v = "http://example.com/tested";
		StatementResult result = sc.filterByVerb("http://example.com/tested").limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getVerb());
			assertTrue(s.getVerb().getId().equals(v) || s.getVerb().getId().equals(Verbs.voided().getId()));
		}
	}

	@Test
	public void testFilterByActor() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Actor a = new Agent();
		a.setMbox(MBOX);
		StatementResult result = sc.filterByActor(a).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getActor());
			assertEquals(a.getMbox(), s.getActor().getMbox());
		}
	}

	@Test
	public void testFilterByActivity() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		StatementResult result = sc.filterByActivity(ACTIVITY_ID).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getObject());
			assertEquals(ACTIVITY_ID, ((Activity) s.getObject()).getId());
		}
	}

	@Test
	public void testFilterByRegistration() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		String reg = UUID.randomUUID().toString();
		Agent a = new Agent();
		a.setMbox(MBOX);
		Verb v = new Verb("http://example.com/tested");
		Activity act = new Activity(ACTIVITY_ID);
		Statement statement = new Statement(a, v, act);
		Context c = new Context();
		c.setRegistration(reg);
		statement.setContext(c);
		String publishedId = sc.postStatement(statement);
		assertTrue(publishedId.length() > 0);

		StatementResult result = sc.filterByRegistration(reg).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testIncludeRelatedActivities() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		ArrayList<Activity> arr = new ArrayList<Activity>();
		arr.add(new Activity("http://caexample.com"));
		Context c = new Context();
		ContextActivities ca = new ContextActivities();
		ca.setCategory(arr);
		c.setContextActivities(ca);
		Statement stmt = new Statement(a, Verbs.asked(), new Activity("http://example.com"));
		stmt.setContext(c);
		String publishedId = sc.postStatement(stmt);
		assertTrue(publishedId.length() > 0);

		StatementResult result = sc.filterByActivity("http://caexample.com").includeRelatedActivities(true)
				.limitResults(10).exact().getStatements();
		assertFalse(result.getStatements().isEmpty());

		result = sc.filterByActivity("http://caexample.com").includeRelatedActivities(false).limitResults(10).exact()
				.getStatements();
		assertTrue(result.getStatements().isEmpty());
	}

	@Test
	public void testIncludeRelatedAgents() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox(MBOX);
		Agent oa = new Agent();
		oa.setMbox("mailto:tester2@example.com");
		Statement stmt = new Statement(a, Verbs.asked(), oa);
		String publishedId = sc.postStatement(stmt);
		assert publishedId.length() > 0;

		StatementResult result = sc.filterByActor(oa).includeRelatedAgents(true).limitResults(10).canonical()
				.getStatements();
		assertFalse(result.getStatements().isEmpty());

		result = sc.filterByActor(oa).includeRelatedAgents(false).limitResults(10).canonical().getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testFilterBySince() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		String dateQuery = "2014-05-02T00:00:00Z";
		Calendar date = javax.xml.bind.DatatypeConverter.parseDateTime(dateQuery);
		StatementResult result = sc.filterBySince(dateQuery).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			Calendar statementTimestamp = javax.xml.bind.DatatypeConverter.parseDateTime(s.getTimestamp());
			// the since date should be less than (denoted by a compareTo value
			// being less than 0
			assertTrue(date.compareTo(statementTimestamp) < 0);
		}
	}

	@Test
	public void testLimitResults() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		StatementResult result = sc.limitResults(1).getStatements();
		assertFalse(result.getStatements().isEmpty());
		assertEquals(result.getStatements().size(), 1);
	}

	@Test
	public void testFilterByUntil() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);
		String dateQuery = df.format(new Date());
		Calendar date = javax.xml.bind.DatatypeConverter.parseDateTime(dateQuery);
		StatementResult result = sc.filterByUntil(dateQuery).limitResults(10).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			Calendar statementTimestamp = javax.xml.bind.DatatypeConverter.parseDateTime(s.getTimestamp());
			// the until date should be greater than (denoted by a compareTo
			// value
			// being greater than 0
			assertTrue(date.compareTo(statementTimestamp) >= 0);
		}
	}

	@Test
	public void testExact() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		StatementResult result = sc.exact().getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testIds() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);

		StatementResult result = sc.limitResults(10).ids().getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testCanonical() throws IOException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		Agent a = new Agent();
		a.setMbox("mailto:tester2@example.com");

		StatementResult result = sc.filterByActor(a).canonical().getStatements();
		assertFalse(result.getStatements().isEmpty());
	}

	@Test
	public void testAscending() throws IOException, ParseException {
		StatementClient sc = new StatementClient(LRS_URI, USERNAME, PASSWORD);
		// Test ascending
		StatementResult result = sc.limitResults(10).ascending(true).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (int i = 0; i < result.getStatements().size() - 1; i++) {
			Calendar firstTimestamp = ISO8601.toCalendar(result.getStatements().get(i).getStored());
			Calendar secondTimestamp = ISO8601.toCalendar(result.getStatements().get(i + 1).getStored());
			assertTrue(firstTimestamp.compareTo(secondTimestamp) < 0 || firstTimestamp.compareTo(secondTimestamp) == 0);
		}

		// Test descending
		result = sc.limitResults(10).ascending(false).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (int i = 0; i < result.getStatements().size() - 1; i++) {
			Calendar firstTimestamp = ISO8601.toCalendar(result.getStatements().get(i).getStored());
			Calendar secondTimestamp = ISO8601.toCalendar(result.getStatements().get(i + 1).getStored());
			assertTrue(firstTimestamp.compareTo(secondTimestamp) > 0 || firstTimestamp.compareTo(secondTimestamp) == 0);
		}
	}

}
