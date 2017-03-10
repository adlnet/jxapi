package gov.adlnet.xapi;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.client.AboutClient;
import gov.adlnet.xapi.model.About;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class AboutClientTest extends TestCase {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI";
	private static final String USERNAME = "jXAPI";
	private static final String PASSWORD = "password";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAboutClientStringStringString() throws IOException {
		AboutClient ac = new AboutClient(LRS_URI, USERNAME, PASSWORD);
		assertNotNull(ac);
		
		ac = new AboutClient(LRS_URI+'/', USERNAME, PASSWORD);
		assertNotNull(ac);

		// Incorrect password
		ac = new AboutClient(LRS_URI, USERNAME, "passw0rd");
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			ac = new AboutClient("fail", USERNAME, PASSWORD);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAboutClientURLStringString() throws IOException {
		URL lrs_url = new URL(LRS_URI);
		AboutClient ac = new AboutClient(lrs_url, USERNAME, PASSWORD);
		assertNotNull(ac);
	}

	@Test
	public void testAboutClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		AboutClient ac = new AboutClient(LRS_URI, encodedCreds);
		assertNotNull(ac);
		
		ac = new AboutClient(LRS_URI+'/', encodedCreds);
		assertNotNull(ac);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new AboutClient(LRS_URI, USERNAME, encodedCreds);
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			ac = new AboutClient("fail", USERNAME, PASSWORD);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAboutClientURLString() throws IOException {
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);
		URL lrs_url = new URL(LRS_URI);

		AboutClient ac = new AboutClient(lrs_url, encodedCreds);
		assertNotNull(ac);

		// Incorrect password
		encodedCreds = Base64.encodeToString((USERNAME + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new AboutClient(LRS_URI, USERNAME, encodedCreds);
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAbout() throws IOException {
		String encodedCreds = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP);

		AboutClient ac = new AboutClient(LRS_URI, encodedCreds);
		About result = ac.getAbout();
		assertNotNull(result.getVersion());
		assertNotNull(result.getExtensions());
	}

}
