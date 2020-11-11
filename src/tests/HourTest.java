package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Hour;

public class HourTest {

	Hour h1;
	Hour h2;
	Hour h3;

	@Before
	public void setUp() {
		h1 = new Hour();
		h2 = new Hour("08:05:25");
		h3 = new Hour(10, 59, 50);
	}

	@Test
	public void testGetTimeString() {
		assertEquals(h1.getTimeString(), "00:00:00");
		assertEquals(h2.getTimeString(), "08:05:25");
		assertEquals(h3.getTimeString(), "10:59:50");
	}

	@Test
	public void testAddTime() {
		h1.addTime(30);
		h2.addTime(35);
		h3.addTime(15);

		assertEquals(h1.getTimeString(), "00:00:30");
		assertEquals(h2.getTimeString(), "08:06:00");
		assertEquals(h3.getTimeString(), "11:00:05");
	}

	@Test
	public void testWaitDurationToWindow() {
		int waitTimePositive = h1.waitDurationToWindow(h2, h3, 10);
		int waitTimeOverDuration = h1.waitDurationToWindow(h2, h3, ((2 * 3600) + (54 * 60) + 26));
		int waitTimeNull = h2.waitDurationToWindow(h1, h3, 100);
		int waitTimeNegative = h2.waitDurationToWindow(h1, h3, ((2 * 3600) + (54 * 60) + 26));

		assertEquals(((8 * 3600) + (5 * 60) + 25), waitTimePositive);
		assertTrue(waitTimeOverDuration < 0);
		assertEquals(0, waitTimeNull);
		assertTrue(waitTimeNegative < 0);
	}
}
