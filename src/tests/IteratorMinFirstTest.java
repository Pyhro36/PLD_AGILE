package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tsp.IteratorMinFirst;

/**
 * Test class for the class IteratorMinFirst
 * 
 * @author Pierre-Louis Lefebvre
 */
public class IteratorMinFirstTest {

	private static Collection<Integer> notSeen;

	private IteratorMinFirst it;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		notSeen = new ArrayList<Integer>();
		for (int i = 1; i < 6; i++) {
			notSeen.add(i);
		}
	}

	@Before
	public void setUp() throws Exception {
		it = new IteratorMinFirst(notSeen, 0, GraphFactoryForTest.getSampleCosts(),
				GraphFactoryForTest.getSampleDurations());
	}

	/**
	 * Test method for
	 * {@link tsp.InteratorMinFirst#hasNext()}.
	 */
	@Test
	public void testHasNext() {
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertFalse(it.hasNext());
	}

	/**
	 * Test method for
	 * {@link tsp.InteratorMinFirst#next()}.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testNext() {
		assertEquals(2, it.next().intValue());
		assertEquals(3, it.next().intValue());
		assertEquals(1, it.next().intValue());
		assertEquals(4, it.next().intValue());
		assertEquals(5, it.next().intValue());
		it.next();
	}
}
