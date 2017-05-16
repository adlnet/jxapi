package gov.adlnet.xapi;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.util.AttachmentAndType;

public class AttachmentAndTypeTest {

	private String type;
	private byte[] attachment;

	@Before
	public void setUp() throws Exception {
		type = "text/plain";
		attachment = "This is a text/plain test.".getBytes("UTF-8");
	}

	@After
	public void tearDown() throws Exception {
		attachment = null;
		attachment = null;
		assertNull(attachment);
		assertNull(attachment);
	}

	@Test
	public void testAttachmentAndType() {
		AttachmentAndType a;

		try {
			a = new AttachmentAndType(attachment, type);
			assertNotNull(a);
		} catch (IllegalArgumentException e) {
			fail("Exception thrown");
		}

		a = null;
		type = null;
		try {
			a = new AttachmentAndType(attachment, type);

		} catch (IllegalArgumentException e) {
			assertTrue(a == null);
		}

		a = null;
		type = "text/plain";
		attachment = null;
		try {
			a = new AttachmentAndType(attachment, type);

		} catch (IllegalArgumentException e) {
			assertTrue(a == null);
		}
	}

	@Test
	public void testGetAttachment() {
		AttachmentAndType a;

		try {
			a = new AttachmentAndType(attachment, type);
			assertNotNull(a);
			assertNotNull(a.getAttachment());
			assertArrayEquals(attachment, a.getAttachment());
		} catch (IllegalArgumentException e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void testGetType() {
		AttachmentAndType a;

		try {
			a = new AttachmentAndType(attachment, type);
			assertNotNull(a);
			assertSame(type, a.getType());
		} catch (IllegalArgumentException e) {
			fail("Exception thrown");
		}
	}

}
