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
		StatementCollection collection = _client.getStatements();
		assert !collection.getStatements().isEmpty();
	}

	public void testPublishStatementWithAgent()
			throws java.net.URISyntaxException,
			java.io.UnsupportedEncodingException, java.io.IOException {
		Statement statement = new Statement();
		Agent agent = new Agent();
		Verb verb  = new Verb();
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
		assert this._client.publishStatement(statement);
	}
}
