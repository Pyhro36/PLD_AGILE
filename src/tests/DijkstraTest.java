package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import shortestpathalgo.Dijkstra;
import shortestpathalgo.Graph;

/**
 * @author Pierre-Louis Test of Dijkstra Algorithm based the OMap implementation
 *         of Graph (considered as true, tested by GraphTest)
 */
public class DijkstraTest {

	private static Graph g;
	private static Dijkstra d;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		g = GraphFactoryForTest.getOMapGraphSample();

		d = new Dijkstra(g, 0);
		d.compute();
	}

	/**
	 * Test method for
	 * {@link shortestpathalgo.Dijkstra#getPredecessor(java.lang.Integer)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetPredecessor() {
		assertEquals(0, d.getPredecessor(1).intValue());
		assertEquals(4, d.getPredecessor(2).intValue());
		assertEquals(4, d.getPredecessor(3).intValue());
		assertEquals(0, d.getPredecessor(4).intValue());
		d.getPredecessor(5);
	}

	/**
	 * Test method for
	 * {@link shortestpathalgo.Dijkstra#getPredecessor(java.lang.Integer)}. the
	 * method should throw an exception if asked predecessor of origin (here 0)
	 */
	@Test(expected = NullPointerException.class)
	public void testGetPredecessorOf0() {
		d.getPredecessor(0);
	}

	/**
	 * Test method for
	 * {@link shortestpathalgo.Dijkstra#getDistance(java.lang.Integer)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetDistance() {
		assertEquals(0, d.getDistance(0).intValue());
		assertEquals(3, d.getDistance(1).intValue());
		assertEquals(9, d.getDistance(2).intValue());
		assertEquals(11, d.getDistance(3).intValue());
		assertEquals(5, d.getDistance(4).intValue());
		d.getDistance(5);
	}
}
