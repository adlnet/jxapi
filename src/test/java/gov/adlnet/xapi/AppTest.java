package gov.adlnet.xapi.model;

import gov.adlnet.xapi.model.*;
import gov.adnlet.xapi.client.StatementClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.google.gson.*;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
	private static final String PASSWORD = "password";
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	private StatementClient _client;

	public AppTest(String testName) throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException {
		super(testName);
		_client = new StatementClient(LRS_URI, USERNAME, PASSWORD);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testGetStatements() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		StatementResult collection = _client.getStatements();
		assert !collection.getStatements().isEmpty();
	}

	public void testGetSingleStatement() throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		String statementId = "23d0a261-fede-4548-9431-314389bc1ebd";
		Statement collection = _client.get(statementId);
		assert collection.getId().equals(statementId);
	}

	public void testPublishStatementWithAgent()
			throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
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
		String publishedId = this._client.publishStatement(statement);
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
}
