package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.model.Account;
import gov.adlnet.xapi.model.Agent;

public class AgentTest {

	private static final String NAME = "jXAPI";
	private static final String MBOX = "mailto:test@example.com";
	private static final String TEST_IRI = "http://example.com";
	private Agent agent = null;

	@Before
	public void setUp() throws Exception {
		agent = new Agent();
	}

	@After
	public void tearDown() throws Exception {
		agent = null;
	}

	@Test
	public void testGetObjectType() {
		String expected = "Agent";
		String actual = agent.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testAgent() {
		Agent agent = new Agent();
		assertNotNull(agent);
	}

	@Test
	public void testAgentStringString() {
		Agent agent = new Agent(NAME, MBOX);
		assertNotNull(agent);
	}

	@Test
	public void testAgentStringURI() throws URISyntaxException {
		URI uri = new URI(TEST_IRI);
		Agent agent = new Agent(NAME, uri);
		assertNotNull(agent);
	}

	@Test
	public void testAgentStringAccount() {
		Account account = new Account(NAME, TEST_IRI);
		Agent agent = new Agent(NAME, account);
		assertNotNull(agent);
	}

}
