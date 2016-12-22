package gov.adlnet.xapi;

import gov.adlnet.xapi.client.AboutClient;
import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public static class ISO8601 {
		private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

		/** Transform Calendar to ISO 8601 string. */
		public static String fromCalendar(final Calendar calendar) {
			Date date = calendar.getTime();
			String formatted = new SimpleDateFormat(dateFormat).format(date);
			return formatted.substring(0, 22) + ":" + formatted.substring(22);
		}

		/** Get current date and time formatted as ISO 8601 string. */
		public static String now() {
			return fromCalendar(GregorianCalendar.getInstance());
		}

		/** Transform ISO 8601 string to Calendar. */
		public static Calendar toCalendar(final String iso8601string)
				throws ParseException {
			Calendar calendar = GregorianCalendar.getInstance();

            String s = "";
            if (iso8601string.contains("'Z'")){
                s = iso8601string.replace("Z", "+00:00");
            }
            else if (!iso8601string.contains("+")){
                s = iso8601string + "+00:00";
            }
            else{
                s = iso8601string;
            }

			Date date = new SimpleDateFormat(dateFormat).parse(s);
			calendar.setTime(date);
			return calendar;
		}
	}
	private static final String LRS_URI = "https://lrs.adlnet.gov/xapi";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */

	public AppTest(String testName) throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException {
		super(testName);

	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test ;-)
	 */
	public void testGetStatements() throws java.net.URISyntaxException,
			java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		StatementResult collection = _client.getStatements();
		assert !collection.getStatements().isEmpty();
	}

    public void testGetMoreStatements() throws URISyntaxException,
            IOException{
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        StatementResult collection = _client.getStatements();
        assert !collection.getStatements().isEmpty();

        String more = collection.getMore();

        if (!more.isEmpty()){
            StatementResult moreCollection = _client.getStatements(more);
            assert !moreCollection.getStatements().isEmpty();
        }
    }

	public void testPutGetSingleStatement() throws java.net.URISyntaxException,
			java.io.IOException {
		String statementId = UUID.randomUUID().toString();
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);

        Statement stmt = new Statement(new Agent("test name", "mailto:testname@testname.com"),
                Verbs.experienced(), new Activity("http://testactivity.testactivity.com"));
        stmt.setId(statementId);
        Boolean put = _client.putStatement(stmt, statementId);
		assertTrue(put);
        Statement collection = _client.get(statementId);
		assert collection.getId().equals(statementId);
	}

    public void testVoidStatement() throws java.net.URISyntaxException,
            java.io.IOException {
        String voidedId = UUID.randomUUID().toString();
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);

        Statement stmt = new Statement(new Agent("test name", "mailto:testname@testname.com"),
                Verbs.experienced(), new Activity("http://testactivity.testactivity.com"));
        stmt.setId(voidedId);
        Boolean put = _client.putStatement(stmt, voidedId);
        assertTrue(put);

        Statement stmt2 = new Statement(new Agent("test name", "mailto:testname@testname.com"),
                Verbs.voided(), new StatementReference(voidedId));
        String postedId = _client.postStatement(stmt2);
        assert postedId.length() > 0;

        Statement collection = _client.getVoided(voidedId);
        assert collection.getId().equals(voidedId);
    }

	public void testPublishStatementWithAgent()
			throws java.net.URISyntaxException, java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Statement statement = new Statement();
		Agent agent = new Agent();
		Verb verb = new Verb();
		verb.setId("http://adlnet.gov/expapi/verbs/experienced");
		agent.setMbox("mailto:test@example.com");
		agent.setName("Tester McTesterson");
		statement.setActor(agent);
		statement.setId(UUID.randomUUID().toString());
		statement.setVerb(verb);
		Activity a = new Activity();
		a.setId("http://example.com");
		statement.setObject(a);
		ActivityDefinition ad = new ActivityDefinition();
		ad.setChoices(new ArrayList<InteractionComponent>());
		InteractionComponent ic = new InteractionComponent();
		ic.setId("http://example.com");
		ic.setDescription(new HashMap<String, String>());
		ic.getDescription().put("en-US", "test");
		ad.getChoices().add(ic);
        ArrayList<String> crp = new ArrayList<String>();
        crp.add("http://example.com");
        ad.setCorrectResponsesPattern(crp);
		ad.setInteractionType("choice");
		ad.setMoreInfo("http://example.com");
		a.setDefinition(ad);
		String publishedId = _client.postStatement(statement);
		assert publishedId.length() > 0;
	}

	public void testSettingMultipeInverseFunctionProperties()
			throws java.net.URISyntaxException, java.io.IOException {
		Agent agent = new Agent();
		agent.setMbox("mailto:test@example.com");
		try {
			agent.setMbox_sha1sum("test13212113");
			assert false;
		} catch (IllegalArgumentException ex) {
			assert true;
		}
	}

    public void testPublishStatementWithAttachmentFileURL()
            throws URISyntaxException, IOException, NoSuchAlgorithmException{
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        Statement statement = new Statement();
        Agent agent = new Agent();
        Verb verb = new Verb();
        verb.setId("http://adlnet.gov/expapi/verbs/experienced");
        agent.setMbox("mailto:test@example.com");
        agent.setName("Tester McTesterson");
        statement.setActor(agent);
        statement.setId(UUID.randomUUID().toString());
        statement.setVerb(verb);
        Activity a = new Activity();
        a.setId("http://attachmentexample.com");
        statement.setObject(a);
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
        a.setDefinition(ad);

        Attachment att = new Attachment();
        HashMap<String, String> attDis = new HashMap<String, String>();
        attDis.put("en-US", "jxapi Test Attachment From FileURL");
        att.setDisplay(attDis);
        URI usageType = new URI("http://example.com/test/usage");
        att.setUsageType(usageType);
        att.setContentType("application/json");
        att.setLength(45);
        att.setFileUrl(new URI("http://test/attachment/url"));
        att.setSha2(generateSha2("http://test/attachment/url".getBytes()));

        ArrayList<Attachment> attList = new ArrayList<Attachment>();
        attList.add(att);
        statement.setAttachments(attList);

        String publishedId = _client.postStatement(statement);
        assert publishedId.length() > 0;
    }

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @org.junit.Test
    public void testPublishStatementWithAttachmentFile()
            throws URISyntaxException, IOException, NoSuchAlgorithmException{
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        Statement statement = new Statement();
        Agent agent = new Agent();
        Verb verb = new Verb();
        verb.setId("http://adlnet.gov/expapi/verbs/experienced");
        agent.setMbox("mailto:test@example.com");
        agent.setName("Tester McTesterson");

        statement.setActor(agent);
        statement.setId(UUID.randomUUID().toString());
        statement.setVerb(verb);

        Activity a = new Activity();
        a.setId("http://attachmentexample.com");
        statement.setObject(a);

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
        a.setDefinition(ad);

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
        att.setLength((int)testFile.length());
        byte[] arr = fileToByteArray(testFile);
        att.setSha2(generateSha2(arr));

        ArrayList<Attachment> attList = new ArrayList<Attachment>();
        attList.add(att);
        statement.setAttachments(attList);

        ArrayList<byte[]> realAtts = new ArrayList<byte[]>();
        realAtts.add(arr);

        String publishedId = _client.postStatementWithAttachment(statement, contentType, realAtts);
        assert publishedId.length() > 0;

        String res = _client.getStatementsWithAttachments();
        assertNotNull(res);

        testFile.deleteOnExit();
    }
    
    private byte[] fileToByteArray(File file) throws IOException {
        byte []buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if ( ios.read(buffer) == -1 ) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                 if ( ios != null )
                      ios.close();
            } catch ( IOException e) {
            }
        }
        return buffer;
    }

    public String generateSha2(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        return new String(Hex.encode(md.digest()));
    }

	public void testQueryByVerb() throws java.net.URISyntaxException,
			java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Verb v = Verbs.experienced();
		StatementResult result = _client.filterByVerb(v).limitResults(10)
                .getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getVerb());
            assertTrue(s.getVerb().getId().equals(v.getId()) || s.getVerb().getId().equals(Verbs.voided().getId()));
		}
	}

	public void testQueryByAgent() throws java.net.URISyntaxException,
			java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Actor a = new Agent();
		a.setMbox("mailto:test@example.com");
		StatementResult result = _client.filterByActor(a).limitResults(10)
                .getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getActor());
			assertEquals(a.getMbox(), s.getActor().getMbox());
		}
	}

    public void testQueryByActivity() throws java.net.URISyntaxException,
            java.io.IOException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        StatementResult result = _client.filterByActivity("http://example.com")
                .limitResults(10).getStatements();
        assertFalse(result.getStatements().isEmpty());
        for (Statement s : result.getStatements()) {
            assertNotNull(s.getObject());
            assertEquals("http://example.com", ((Activity)s.getObject()).getId());
        }
    }

    public void testQueryByRelatedAgent() throws java.net.URISyntaxException,
            java.io.IOException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        Agent a = new Agent();
        a.setMbox("mailto:test@example.com");
        Agent oa = new Agent();
        oa.setMbox("mailto:tester2@example.com");
        Statement stmt = new Statement(a, Verbs.asked(), oa);
        String publishedId = _client.postStatement(stmt);
        assert  publishedId.length() > 0;

        StatementResult result = _client.filterByActor(oa).includeRelatedAgents(true)
                .limitResults(10).canonical().getStatements();
        assertFalse(result.getStatements().isEmpty());
    }

    public void testQueryByRelatedActivity() throws java.net.URISyntaxException,
            java.io.IOException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        Agent a = new Agent();
        a.setMbox("mailto:test@example.com");
        ArrayList<Activity> arr = new ArrayList<Activity>();
        arr.add(new Activity("http://caexample.com"));
        Context c = new Context();
        ContextActivities ca = new ContextActivities();
        ca.setCategory(arr);
        c.setContextActivities(ca);
        Statement stmt = new Statement(a, Verbs.asked(), new Activity("http://example.com"));
        stmt.setContext(c);
        String publishedId = _client.postStatement(stmt);
        assert  publishedId.length() > 0;

        StatementResult result = _client.filterByActivity("http://caexample.com")
                .includeRelatedActivities(true)
                .limitResults(10).exact().getStatements();
        assertFalse(result.getStatements().isEmpty());
    }

    public void testQueryByRegistration() throws java.net.URISyntaxException,
            java.io.IOException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);
        String reg = UUID.randomUUID().toString();
        Agent a = new Agent();
        a.setMbox("mailto:test@example.com");
        Statement stmt = new Statement(a, Verbs.experienced(),
                new Activity("http://example.com"));
        Context c = new Context();
        c.setRegistration(reg);
        stmt.setContext(c);
        String publishedId = _client.postStatement(stmt);
        assert  publishedId.length() > 0;

        StatementResult result = _client.filterByRegistration(reg)
                .limitResults(10).getStatements();
        assertFalse(result.getStatements().isEmpty());

    }

    public void testQueryByLimit() throws java.net.URISyntaxException,
            java.io.IOException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);

        StatementResult result = _client.limitResults(1).getStatements();
        assertFalse(result.getStatements().isEmpty());
        assertEquals(result.getStatements().size(), 1);
    }

	public void testQueryByAgentAndVerb() throws java.net.URISyntaxException,
			java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Actor a = new Agent();
		a.setMbox("mailto:test@example.com");
		StatementResult result = _client.filterByVerb("http://adlnet.gov/expapi/verbs/experienced")
                .limitResults(10).filterByActor(a).ids()
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getActor());
			assertEquals(a.getMbox(), s.getActor().getMbox());
			assertNotNull(s.getVerb());
			assertEquals("http://adlnet.gov/expapi/verbs/experienced", s.getVerb().getId());
		}
	}

	public void testQueryBySince() throws java.net.URISyntaxException,
			java.io.IOException, ParseException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		String dateQuery = "2014-05-02T00:00:00Z";
        Calendar date = javax.xml.bind.DatatypeConverter.parseDateTime(dateQuery);
        StatementResult result = _client.filterBySince(dateQuery).limitResults(10)
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
            Calendar statementTimestamp = javax.xml.bind.DatatypeConverter.parseDateTime(s.getTimestamp());
			// the since date should be less than (denoted by a compareTo value
			// being less than 0
			assert date.compareTo(statementTimestamp) < 0;
		}
	}

	public void testQueryByUntil() throws java.net.URISyntaxException,
			java.io.IOException, ParseException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String dateQuery = df.format(new Date());
		Calendar date = javax.xml.bind.DatatypeConverter.parseDateTime(dateQuery);
		StatementResult result = _client.filterByUntil(dateQuery).limitResults(10)
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
            Calendar statementTimestamp = javax.xml.bind.DatatypeConverter.parseDateTime(s.getTimestamp());
			// the until date should be greater than (denoted by a compareTo value
			// being greater than 0
			assert date.compareTo(statementTimestamp) >= 0;
		}
	}

    public void testQueryByAscending() throws java.net.URISyntaxException,
            java.io.IOException, ParseException {
        StatementClient _client = new StatementClient(LRS_URI, USERNAME,
                PASSWORD);

        StatementResult result = _client.limitResults(10).ascending(true)
                .getStatements();
        assertFalse(result.getStatements().isEmpty());
        for (int i=0; i<result.getStatements().size() - 1; i++){
            Calendar firstTimestamp = ISO8601.toCalendar(result.getStatements().get(i).getTimestamp());
            Calendar secondTimestamp = ISO8601.toCalendar(result.getStatements().get(i+1).getTimestamp());
            assert (firstTimestamp.compareTo(secondTimestamp) < 0 || firstTimestamp.compareTo(secondTimestamp) == 0);
        }
    }

    public void testAbout() throws URISyntaxException, IOException, ParseException{
        AboutClient _client = new AboutClient(LRS_URI, USERNAME,
                PASSWORD);
        About result = _client.getAbout();
        assertNotNull(result.getVersion());
        assertNotNull(result.getExtensions());
    }
}
