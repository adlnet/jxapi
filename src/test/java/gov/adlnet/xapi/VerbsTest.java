package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.Verbs;

public class VerbsTest {

	@Test
	public void testAnswered() {
		Verb actual = Verbs.answered();
		assertNotNull(actual);
		assertEquals("answered", actual.toString());
	}

	@Test
	public void testAsked() {
		Verb actual = Verbs.asked();
		assertNotNull(actual);
		assertEquals("asked", actual.toString());
	}

	@Test
	public void testAttempted() {
		Verb actual = Verbs.attempted();
		assertNotNull(actual);
		assertEquals("attempted", actual.toString());
	}

	@Test
	public void testAttended() {
		Verb actual = Verbs.attended();
		assertNotNull(actual);
		assertEquals("attended", actual.toString());
	}

	@Test
	public void testCommented() {
		Verb actual = Verbs.commented();
		assertNotNull(actual);
		assertEquals("commented", actual.toString());
	}

	@Test
	public void testCompleted() {
		Verb actual = Verbs.completed();
		assertNotNull(actual);
		assertEquals("completed", actual.toString());
	}

	@Test
	public void testExited() {
		Verb actual = Verbs.exited();
		assertNotNull(actual);
		assertEquals("exited", actual.toString());
	}

	@Test
	public void testExperienced() {
		Verb actual = Verbs.experienced();
		assertNotNull(actual);
		assertEquals("experienced", actual.toString());
	}

	@Test
	public void testFailed() {
		Verb actual = Verbs.failed();
		assertNotNull(actual);
		assertEquals("failed", actual.toString());
	}

	@Test
	public void testImported() {
		Verb actual = Verbs.imported();
		assertNotNull(actual);
		assertEquals("imported", actual.toString());
	}

	@Test
	public void testInitialized() {
		Verb actual = Verbs.initialized();
		assertNotNull(actual);
		assertEquals("initialized", actual.toString());
	}

	@Test
	public void testInteracted() {
		Verb actual = Verbs.interacted();
		assertNotNull(actual);
		assertEquals("interacted", actual.toString());
	}

	@Test
	public void testLaunched() {
		Verb actual = Verbs.launched();
		assertNotNull(actual);
		assertEquals("launched", actual.toString());
	}

	@Test
	public void testMastered() {
		Verb actual = Verbs.mastered();
		assertNotNull(actual);
		assertEquals("mastered", actual.toString());
	}

	@Test
	public void testPassed() {
		Verb actual = Verbs.passed();
		assertNotNull(actual);
		assertEquals("passed", actual.toString());
	}

	@Test
	public void testPreferred() {
		Verb actual = Verbs.preferred();
		assertNotNull(actual);
		assertEquals("preferred", actual.toString());
	}

	@Test
	public void testProgressed() {
		Verb actual = Verbs.progressed();
		assertNotNull(actual);
		assertEquals("progressed", actual.toString());
	}

	@Test
	public void testRegistered() {
		Verb actual = Verbs.registered();
		assertNotNull(actual);
		assertEquals("registered", actual.toString());
	}

	@Test
	public void testResponded() {
		Verb actual = Verbs.responded();
		assertNotNull(actual);
		assertEquals("responded", actual.toString());
	}

	@Test
	public void testResumed() {
		Verb actual = Verbs.resumed();
		assertNotNull(actual);
		assertEquals("resumed", actual.toString());
	}

	@Test
	public void testScored() {
		Verb actual = Verbs.scored();
		assertNotNull(actual);
		assertEquals("scored", actual.toString());
	}

	@Test
	public void testShared() {
		Verb actual = Verbs.shared();
		assertNotNull(actual);
		assertEquals("shared", actual.toString());
	}

	@Test
	public void testSuspended() {
		Verb actual = Verbs.suspended();
		assertNotNull(actual);
		assertEquals("suspended", actual.toString());
	}

	@Test
	public void testTerminated() {
		Verb actual = Verbs.terminated();
		assertNotNull(actual);
		assertEquals("terminated", actual.toString());
	}

	@Test
	public void testVoided() {
		Verb actual = Verbs.voided();
		assertNotNull(actual);
		assertEquals("voided", actual.toString());
	}

}
