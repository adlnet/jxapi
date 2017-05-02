package gov.adlnet.xapi;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Account;
import gov.adlnet.xapi.model.Person;

public class PersonTest {
	private static final String[] NAME = {"jXAPI"};
	private static final String[] MBOX = {"mailto:test@example.com"};
	private static final String[] TEST_IRI = {"http://example.com"};
	private static final String[] MBOX_SHA1SUM = {"test1234"};

	Person person = null;
	
	private String toString(String[] input){
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
		   strBuilder.append(input[i]);
		}
		return strBuilder.toString();
	}
	
	@Before
	public void setUp() throws Exception {
		person = new Person();
		person.setName(NAME);
		person.setMbox(MBOX);
	}

	@After
	public void tearDown() throws Exception {
		person = null;
	}

	@Test
	public void testGetObjectType() {
		String expected = "Person";
		String actual = person.getObjectType();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetName() {
		String[] expected = NAME;
		String[] actual = person.getName();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testSetName() {
		String[] expected = {"New"};
		person.setName(expected);
		String[] actual = person.getName();
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetMbox() {
		String[] expected = MBOX;
		String[] actual = person.getMbox();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testSetMbox() {
		String[] expected = {"mailto:new_test@example.com"};
		try {
			person.setMbox(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		person.setMbox(null);
		person.setMbox(expected);
		String[] actual = person.getMbox();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetMbox_sha1sum() {
		String[] expected = MBOX_SHA1SUM;
		person.setMbox(null);
		person.setMbox_sha1sum(expected);
		String[] actual = person.getMbox_sha1sum();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testSetMbox_sha1sum() {
		String[] expected = MBOX_SHA1SUM;
		try {
			person.setMbox_sha1sum(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		person.setMbox(null);
		person.setMbox_sha1sum(expected);
		String[] actual = person.getMbox_sha1sum();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetOpenid() {
		String[] expected = TEST_IRI;
		person.setMbox(null);
		person.setOpenid(expected);
		String[] actual = person.getOpenid();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testSetOpenId() {
		String[] expected = TEST_IRI;
		try {
			person.setOpenid(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		person.setMbox(null);
		person.setOpenid(expected);
		String[] actual = person.getOpenid();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetAccount() {
		String name = toString(NAME);
		String iri = toString(TEST_IRI);
		Account account = new Account(name, iri);
		Account[] expected = {account};
		person.setMbox(null);
		person.setAccount(expected);
		Account[] actual = person.getAccount();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testSetAccount() {
		String name = "New";
		String iri = "http://new.example.com";
		Account account = new Account(name, iri);
		Account[] expected = {account};
		try {
			person.setAccount(expected);
			assert (false);
		} catch (IllegalArgumentException i) {
			assert (true);
		}
		person.setMbox(null);
		person.setAccount(expected);
		Account[] actual = person.getAccount();
		assertNotNull(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testToString() {
		
		String name = toString(NAME);
		String mbox = toString(MBOX);
		String mbox_sha1sum = toString(MBOX_SHA1SUM);
		String iri = toString(TEST_IRI);		
		
		String actual = person.toString();
		assertNotNull(actual);
		assert (actual.contains(name));
		assert (actual.contains(mbox));

		person.setMbox(null);
		actual = person.toString();
		assert (!actual.contains(mbox));
		actual = person.toString();
		assert (!actual.contains(mbox));
		person.setMbox(null);

		person.setMbox_sha1sum(MBOX_SHA1SUM);
		actual = person.toString();
		assert (actual.contains(mbox_sha1sum));
		person.setMbox_sha1sum(null);
		actual = person.toString();
		assert (!actual.contains(mbox_sha1sum));
		person.setMbox_sha1sum(null);

		person.setOpenid(TEST_IRI);
		actual = person.toString();
		assert (actual.contains(iri));
		person.setOpenid(null);

		Account account = new Account(name, iri);
		Account[] acc = {account};
		person.setAccount(acc);
		actual = person.toString();
		assert (actual.contains(iri));
		assert (actual.contains(name));
		person.setAccount(null);
		
	}
	
	@Test
	public void testSerialize() throws URISyntaxException {
		String expected = toString(MBOX);
		JsonElement actual = person.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("mbox").toString().contains(expected));
		person.setMbox(null);

		String[] strArray = MBOX_SHA1SUM;
		expected = toString(MBOX_SHA1SUM);
		person.setMbox_sha1sum(strArray);
		actual = person.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("mbox_sha1sum").toString().contains(expected));
		person.setMbox_sha1sum(null);

		strArray = TEST_IRI;
		expected = toString(TEST_IRI);
		person.setOpenid(strArray);
		actual = person.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("openid").toString().contains(expected.toString()));
		person.setOpenid(null);

		String name = toString(NAME);
		String iri = toString(TEST_IRI);	
		Account account = new Account(name, iri);
		Account[] expectedAccount = {account};
		person.setAccount(expectedAccount);
		actual = person.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		obj = (JsonObject) actual;
		assert (obj.get("account").toString().contains(expectedAccount[0].getName()));
		person.setAccount(null);

	}

}
