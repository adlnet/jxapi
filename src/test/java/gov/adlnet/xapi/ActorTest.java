package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Account;
import gov.adlnet.xapi.model.Agent;

public class ActorTest {

	private static final String NAME = "jXAPI";
	private static final String MBOX = "mailto:test@example.com";
	private static final String TEST_IRI = "http://example.com";
	private static final String MBOX_SHA1SUM = "test1234";
	private Agent agent = null;

	@Before
	public void setUp() throws Exception {
		agent = new Agent(NAME, MBOX);

	}

	@After
	public void tearDown() throws Exception {
		agent = null;
	}

	@Test
	public void testGetMbox() {
		String expected = MBOX;
		String actual = agent.getMbox();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetMbox() {
		String expected = "mailto:new_test@example.com";
		try {
			agent.setMbox(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		agent.setMbox(null);
		agent.setMbox(expected);
		String actual = agent.getMbox();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMbox_sha1sum() {
		String expected = MBOX_SHA1SUM;
		agent.setMbox(null);
		agent.setMbox_sha1sum(expected);
		String actual = agent.getMbox_sha1sum();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetMbox_sha1sum() {
		String expected = MBOX_SHA1SUM;
		try {
			agent.setMbox_sha1sum(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		agent.setMbox(null);
		agent.setMbox_sha1sum(expected);
		String actual = agent.getMbox_sha1sum();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetOpenid() throws URISyntaxException {
		URI expected = new URI(TEST_IRI);
		agent.setMbox(null);
		agent.setOpenid(expected);
		URI actual = agent.getOpenid();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetOpenid() throws URISyntaxException {
		URI expected = new URI(TEST_IRI);
		try {
			agent.setOpenid(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		agent.setMbox(null);
		agent.setOpenid(expected);
		URI actual = agent.getOpenid();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAccount() {
		Account expected = new Account(NAME, TEST_IRI);
		agent.setMbox(null);
		agent.setAccount(expected);
		Account actual = agent.getAccount();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetAccount() {
		Account expected = new Account("New", "http://new.example.com");
		try {
			agent.setAccount(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		agent.setMbox(null);
		agent.setAccount(expected);
		Account actual = agent.getAccount();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetName() {
		String expected = NAME;
		String actual = agent.getName();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSetName() {
		String expected = "new name";
		agent.setName(expected);
		String actual = agent.getName();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetObjectType() {
		String expected = "Agent";
		String actual = agent.getObjectType();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testSerialize() throws URISyntaxException {
		String expected = MBOX;
		JsonElement actual = agent.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("mbox").toString().contains(expected));
		agent.setMbox(null);

		expected = MBOX_SHA1SUM;
		agent.setMbox_sha1sum(expected);
		actual = agent.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("mbox_sha1sum").toString().contains(expected));
		agent.setMbox_sha1sum(null);

		URI expectedURI = new URI(TEST_IRI);
		agent.setOpenid(expectedURI);
		actual = agent.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("openid").toString().contains(expectedURI.toString()));
		agent.setOpenid(null);

		Account account = new Account(NAME, TEST_IRI);
		agent.setAccount(account);
		actual = agent.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("account").toString().contains(account.getName()));
		agent.setAccount(null);

	}

	@Test
	public void testToString() throws URISyntaxException {
		String actual = agent.toString();
		assertNotNull(actual);
		assert (actual.contains(NAME));
		assert (actual.contains(MBOX));
		agent.setName("");
		actual = agent.toString();
		assert (actual.contains("Anonymous"));
		assert (actual.contains(MBOX));
		agent.setName(null);
		actual = agent.toString();
		assert (actual.contains("Anonymous"));
		assert (actual.contains(MBOX));

		agent.setMbox(null);
		actual = agent.toString();
		assert (!actual.contains(MBOX));
		agent.setMbox("");
		actual = agent.toString();
		assert (!actual.contains(MBOX));
		agent.setMbox(null);

		agent.setMbox_sha1sum(MBOX_SHA1SUM);
		actual = agent.toString();
		assert (actual.contains(MBOX_SHA1SUM));
		agent.setMbox_sha1sum(null);
		agent.setMbox_sha1sum("");
		actual = agent.toString();
		assert (!actual.contains(MBOX_SHA1SUM));
		agent.setMbox_sha1sum(null);

		URI uri = new URI(TEST_IRI);
		agent.setOpenid(uri);
		actual = agent.toString();
		assert (actual.contains(TEST_IRI));
		agent.setOpenid(null);

		uri = new URI("");
		agent.setOpenid(uri);
		actual = agent.toString();
		assert (!actual.contains(TEST_IRI));
		agent.setOpenid(null);

		Account account = new Account(NAME, TEST_IRI);
		agent.setAccount(account);
		actual = agent.toString();
		assert (actual.contains(TEST_IRI));
		assert (actual.contains(NAME));
		agent.setAccount(null);

	}

}
