package gov.adlnet.xapi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.client.AboutClient;
import gov.adlnet.xapi.model.About;
import gov.adlnet.xapi.util.Base64;
import junit.framework.TestCase;

public class AboutClientTest extends TestCase {
	
	private String lrs_uri = null;
	private String username = null;
	private String password = null;

	@Before
	public void setUp() throws Exception {
		Properties p = new Properties();
		p.load(new FileReader(new File("../jxapi/src/test/java/config/config.properties")));
		lrs_uri = p.getProperty("lrs_uri");
		username = p.getProperty("username");
		password = p.getProperty("password");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAboutClientStringStringString() throws IOException {
		AboutClient ac = new AboutClient(lrs_uri, username, password);
		assertNotNull(ac);
		
		ac = new AboutClient(lrs_uri+'/', username, password);
		assertNotNull(ac);

		// Incorrect password
		ac = new AboutClient(lrs_uri, username, "passw0rd");
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}

		// Non URL parameter
		try {
			ac = new AboutClient("fail", username, password);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAboutClientURLStringString() throws IOException {
		URL lrs_url = new URL(lrs_uri);
		AboutClient ac = new AboutClient(lrs_url, username, password);
		assertNotNull(ac);
	}

	@Test
	public void testAboutClientStringString() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		AboutClient ac = new AboutClient(lrs_uri, encodedCreds);
		assertNotNull(ac);
		
		ac = new AboutClient(lrs_uri+'/', encodedCreds);
		assertNotNull(ac);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new AboutClient(lrs_uri, username, encodedCreds);
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}
		encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		// Non URL parameter
		try {
			ac = new AboutClient("fail", username, password);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAboutClientURLString() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
		URL lrs_url = new URL(lrs_uri);

		AboutClient ac = new AboutClient(lrs_url, encodedCreds);
		assertNotNull(ac);

		// Incorrect password
		encodedCreds = Base64.encodeToString((username + ":" + "passw0rd").getBytes(), Base64.NO_WRAP);
		ac = new AboutClient(lrs_uri, username, encodedCreds);
		try {
			ac.getAbout();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAbout() throws IOException {
		String encodedCreds = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);

		AboutClient ac = new AboutClient(lrs_uri, encodedCreds);
		About result = ac.getAbout();
		assertNotNull(result.getVersion());
		assertNotNull(result.getExtensions());
	}

}
