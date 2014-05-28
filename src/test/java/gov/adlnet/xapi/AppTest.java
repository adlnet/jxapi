package gov.adlnet.xapi;

import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.InteractionComponent;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.Verbs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
			String s = iso8601string.replace("Z", "+00:00");
			try {
				s = s.substring(0, 22) + s.substring(23); // to get rid of the
															// ":"
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException("Invalid length", 0);
			}
			Date date = new SimpleDateFormat(dateFormat).parse(s);
			calendar.setTime(date);
			return calendar;
		}
	}

	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
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
			java.io.UnsupportedEncodingException, java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		StatementResult collection = _client.getStatements();
		assert !collection.getStatements().isEmpty();
	}

	public void testGetSingleStatement() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		String statementId = "23d0a261-fede-4548-9431-314389bc1ebd";
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Statement collection = _client.get(statementId);
		assert collection.getId().equals(statementId);
	}

	public void testPublishStatementWithAgent()
			throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
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
		ad.setInteractionType("choice");
		ad.setMoreInfo("http://example.com");
		a.setDefinition(ad);
		String publishedId = _client.publishStatement(statement);
		assert publishedId.length() > 0;
	}

	public void testSettingMultipeInverseFunctionProperties()
			throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		Agent agent = new Agent();
		agent.setMbox("mailto:test@example.com");
		try {
			agent.setMbox_sha1sum("test13212113");
			assert false;
		} catch (IllegalArgumentException ex) {
			assert true;
		}
	}

	public void testQueryByVerb() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Verb v = Verbs.experienced();
		StatementResult result = _client.filterByVerb(v).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getVerb());
			assertEquals(v.getId(), s.getVerb().getId());
		}
	}

	public void testQueryByAgent() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Actor a = new Agent();
		a.setMbox("mailto:test@example.com");
		StatementResult result = _client.filterByActor(a).getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getActor());
			assertEquals(a.getMbox(), s.getActor().getMbox());
		}
	}

	public void testQueryByAgentAndVerb() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		Actor a = new Agent();
		a.setMbox("mailto:test@example.com");
		Verb v = Verbs.experienced();
		StatementResult result = _client.filterByVerb(v).filterByActor(a).ids()
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			assertNotNull(s.getActor());
			assertEquals(a.getMbox(), s.getActor().getMbox());
			assertNotNull(s.getVerb());
			assertEquals(v.getId(), s.getVerb().getId());
		}
	}

	public void testQueryBySince() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException,
			ParseException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		String dateQuery = "2014-05-02T17:28:47.000000+00:00";
		Calendar date = ISO8601.toCalendar(dateQuery);
		StatementResult result = _client.filterBySince(dateQuery)
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			Calendar statementTimestampe = ISO8601.toCalendar(s.getTimestamp());
			// the since date should be less than (denoted by a compareTo value
			// being less than 0
			assert date.compareTo(statementTimestampe) < 0;
		}
	}

	public void testQueryByUntil() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException,
			ParseException {
		StatementClient _client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
		String dateQuery = "2014-05-02T17:28:47.000000+00:00";
		Calendar date = ISO8601.toCalendar(dateQuery);
		StatementResult result = _client.filterByUntil(dateQuery)
				.getStatements();
		assertFalse(result.getStatements().isEmpty());
		for (Statement s : result.getStatements()) {
			Calendar statementTimestampe = ISO8601.toCalendar(s.getTimestamp());
			// the until date should be greater than (denoted by a compareTo value
			// being greater than 0
			assert date.compareTo(statementTimestampe) >= 0;
		}
	}
}
