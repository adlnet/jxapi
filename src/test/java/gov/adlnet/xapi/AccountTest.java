package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Account;

public class AccountTest {
	private Account account = null;
	private String expectedName = "Test Name";
	private String expectedHomepage = "htt://test.com";

	@Before
	public void setUp() throws Exception {

		account = new Account(expectedName, expectedHomepage);
	}

	@After
	public void tearDown() throws Exception {
		account = null;
	}

	@Test
	public void testAccount() {
		Account account = new Account();
		assertNotNull(account);
	}

	@Test
	public void testAccountStringString() {
		String name = "Test Name";
		String homepage = "htt://test.com";
		Account account = new Account(name, homepage);
		assertNotNull(account);
	}

	@Test
	public void testGetHomePage() {
		String actualHomepage = account.getHomePage();
		assertNotNull(actualHomepage);
		assertEquals(expectedHomepage, actualHomepage);
	}

	@Test
	public void testSetHomePage() {
		String newHomepage = "http://newpage.com";
		account.setHomePage(newHomepage);
		String actualHomepage = account.getHomePage();
		assertNotNull(actualHomepage);
		assertEquals(newHomepage, actualHomepage);
		assertFalse(expectedHomepage.equals(actualHomepage));
	}

	@Test
	public void testGetName() {
		String actualName = account.getName();
		assertNotNull(actualName);
		assertEquals(expectedName, actualName);
	}

	@Test
	public void testSetName() {
		String newName = "New Name";
		account.setName(newName);
		String actualName = account.getName();
		assertNotNull(actualName);
		assertEquals(newName, actualName);
		assertFalse(expectedName.equals(actualName));
	}

	@Test
	public void testSerialize() {
		JsonElement actual = account.serialize();
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assertEquals(obj.getAsJsonPrimitive("name").getAsString(), "Test Name");
		assertEquals(obj.getAsJsonPrimitive("homePage").getAsString(), "htt://test.com");
	}

	@Test
	public void testToString() {
		String actual = account.toString();
		assertNotNull(actual);
		assert (actual.contains(expectedName));
		assert (actual.contains(expectedHomepage));
	}

}
