package gov.adlnet.xapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Score;

public class ScoreTest {

	private static final float MAX = 10;
	private static final float MIN = 0;
	private static final float RAW = 6;
	private static final float SCALED = 1;
	private Score score = null;

	@Before
	public void setUp() throws Exception {
		score = new Score();
		score.setMax(MAX);
		score.setMin(MIN);
		score.setRaw(RAW);
		score.setScaled(SCALED);
	}

	@After
	public void tearDown() throws Exception {
		score = null;
	}

	@Test
	public void testGetScaled() {
		float expected = SCALED;
		float actual = score.getScaled();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testSetScaled() {
		float expected = 2;
		score.setScaled(expected);
		float actual = score.getScaled();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testGetRaw() {
		float expected = RAW;
		float actual = score.getRaw();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testSetRaw() {
		float expected = 7;
		score.setRaw(expected);
		float actual = score.getRaw();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testGetMin() {
		float expected = MIN;
		float actual = score.getMin();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testSetMin() {
		float expected = 1;
		score.setMin(expected);
		float actual = score.getMin();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testGetMax() {
		float expected = MAX;
		float actual = score.getMax();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testSetMax() {
		float expected = 9;
		score.setMax(expected);
		float actual = score.getMax();
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testSerialize() {
		JsonElement actual = score.serialize();
		assertNotNull(actual);
		assert (actual.isJsonObject());
		JsonObject obj = (JsonObject) actual;
		assert (obj.get("scaled").toString().contains(String.valueOf(SCALED)));
		assert (obj.get("raw").toString().contains(String.valueOf(RAW)));
		assert (obj.get("min").toString().contains(String.valueOf(MIN)));
		assert (obj.get("max").toString().contains(String.valueOf(MAX)));
	}

}
