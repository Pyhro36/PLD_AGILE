package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import shortestpathalgo.Graph;

/**
 * @author Pierre-Louis
 *
 */
public class GraphTest {

	private static Graph g;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		g = GraphFactoryForTest.getOMapGraphSample(); // test Map as a Graph
	}

	/**
	 * Test method for
	 * {@link model.OMap#release(java.lang.Integer, java.lang.Integer, java.util.Map, java.util.Map)}.
	 * case of release of inexistent arc
	 */
	@Test(expected = NullPointerException.class)
	public final void testFalseRelease() {
		Map<Integer, Integer> distances = new HashMap<Integer, Integer>();
		Map<Integer, Integer> predecessors = new HashMap<Integer, Integer>();
		g.release(0, 2, predecessors, distances);
	}

	/**
	 * Test method for
	 * {@link model.OMap#release(java.lang.Integer, java.lang.Integer, java.util.Map, java.util.Map)}.
	 */
	@Test
	public final void testRelease() {
		Map<Integer, Integer> distances = new HashMap<Integer, Integer>();
		Map<Integer, Integer> predecessors = new HashMap<Integer, Integer>();
		distances.put(0, 0);

		g.release(0, 1, predecessors, distances);

		if (distances.containsKey(1)) {
			assertEquals(3, distances.get(1).intValue());

		} else {
			fail("no distance in release 0 to 1");
		}

		if (predecessors.containsKey(1)) {
			assertEquals(0, predecessors.get(1).intValue());
		} else {
			fail("no predecessor in release 0 to 1");
		}

		assertNull(predecessors.get(2));
		assertNull(distances.get(2));

		g.release(1, 2, predecessors, distances);

		if (distances.containsKey(2)) {
			assertEquals(10, distances.get(2).intValue());

		} else {
			fail("no distance in release 1 to 2");
		}

		if (predecessors.containsKey(2)) {
			assertEquals(1, predecessors.get(2).intValue());
		} else {
			fail("no predecessor in release 1 to 2");
		}

		g.release(4, 2, predecessors, distances);

		if (distances.containsKey(2)) {
			assertEquals(10, distances.get(2).intValue());

		} else {
			fail("no distance in release 1 to 2");
		}

		if (predecessors.containsKey(2)) {
			assertEquals(1, predecessors.get(2).intValue());
		} else {
			fail("no predecessor in release 1 to 2");
		}

		g.release(0, 4, predecessors, distances);
		g.release(4, 2, predecessors, distances);

		if (distances.containsKey(2)) {
			assertEquals(9, distances.get(2).intValue());

		} else {
			fail("no distance in release 4 to 2");
		}

		if (predecessors.containsKey(2)) {
			assertEquals(4, predecessors.get(2).intValue());
		} else {
			fail("no predecessor in release 4 to 2");
		}

		g.release(4, 3, predecessors, distances);
		g.release(3, 2, predecessors, distances);

		if (distances.containsKey(2)) {
			assertEquals(9, distances.get(2).intValue());

		} else {
			fail("no distance in release 7 to 2");
		}

		if (predecessors.containsKey(2)) {
			assertEquals(4, predecessors.get(2).intValue());
		} else {
			fail("no predecessor in release 7 to 2");
		}
	}

	/**
	 * Test method for {@link model.OMap#getSuccessors(java.lang.Integer)}.
	 */
	@Test
	public final void testGetSuccessors() {
		List<Integer> successorsOf4 = g.getSuccessors(4);
		assertEquals(3, successorsOf4.size());
		assertEquals(1, successorsOf4.get(0).intValue());
		assertEquals(2, successorsOf4.get(1).intValue());
		assertEquals(3, successorsOf4.get(2).intValue());
	}
}
