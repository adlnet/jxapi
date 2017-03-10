package gov.adlnet.xapi;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.adlnet.xapi.util.AttachmentAndType;

public class AttachmentAndTypeTest {

	private String type;
	private byte[] bytes;
	private ArrayList<byte[]> attachment;

	@Before
	public void setUp() throws Exception {
		type = "text/plain";
		bytes = new byte[] { 84, 104, 105, 115, 32, 105, 115, 32, 116, 104, 101, 32, 102, 105, 114, 115, 116, 32, 108,
				105, 110, 101, 10, 84, 104, 105, 115, 32, 105, 115, 32, 116, 104, 101, 32, 115, 101, 99, 111, 110, 100,
				32, 108, 105, 110, 101, 33, 33, 33, 10, 53, 57, 56, 52, 57, 52, 98, 99, 45, 52, 48, 49, 102, 45, 52, 57,
				102, 53, 45, 98, 50, 102, 101, 45, 97, 49, 98, 99, 53, 48, 102, 50, 100, 50, 100, 49 };
		attachment = new ArrayList<byte[]>();
	}

	@After
	public void tearDown() throws Exception {
		bytes = null;
		attachment = null;
		assertNull(bytes);
		assertNull(attachment);
	}

	@Test
	public void testAttachmentAndType() {
		AttachmentAndType a;
		attachment.add(bytes);

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
		attachment.add(bytes);

		try {
			a = new AttachmentAndType(attachment, type);
			assertNotNull(a);
			assertNotNull(a.getAttachment());
			assertArrayEquals(bytes, a.getAttachment().get(0));
		} catch (IllegalArgumentException e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void testGetType() {
		AttachmentAndType a;
		attachment.add(bytes);

		try {
			a = new AttachmentAndType(attachment, type);
			assertNotNull(a);
			assertSame(type, a.getType());
		} catch (IllegalArgumentException e) {
			fail("Exception thrown");
		}
	}

}
