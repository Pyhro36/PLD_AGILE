package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Hour;
import tsp.TSP;
import tsp.TSP3Observable;

/**
 * Test of the TSP, here implemented with TSP3
 * 
 * @author Pierre-Louis
 *
 */
public class TSPTest {

	private static TSP tspWithoutTimeWindows;
	private static TSP tspWithTimeWindows;
	private static TSP impossibleTspWithTimeWindows;
	private static int[] durations;
	private static int[][] costs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tspWithoutTimeWindows = new TSP3Observable();
		tspWithTimeWindows = new TSP3Observable();
		impossibleTspWithTimeWindows = new TSP3Observable();
		durations = GraphFactoryForTest.getSampleDurations();
		costs = GraphFactoryForTest.getSampleCosts();
		Hour departureTime = GraphFactoryForTest.getDepartureTime();
		Hour[] timeWindowsStarts = GraphFactoryForTest.getSampleTimeWindowMin();
		Hour[] timeWindowsEnds = GraphFactoryForTest.getSampleTimeWindowMaxValid();
		Hour[] impossibleTimeWindowsEnds = GraphFactoryForTest.getImpossibleTimeWindowMax();

		tspWithoutTimeWindows.searchSolution(Integer.MAX_VALUE, durations.length, costs, durations,
				new Hour[durations.length], new Hour[durations.length], departureTime);
		tspWithTimeWindows.searchSolution(Integer.MAX_VALUE, durations.length, costs, durations, timeWindowsStarts,
				timeWindowsEnds, departureTime);
		impossibleTspWithTimeWindows.searchSolution(Integer.MAX_VALUE, durations.length, costs, durations,
				timeWindowsStarts, impossibleTimeWindowsEnds, departureTime);
	}

	/**
	 * Test method for {@link tsp.TSP#getBestSolution()}.
	 */
	@Test
	public void testGetBestSolution() {
		assertEquals(0, tspWithoutTimeWindows.getBestSolution(0).intValue());
		assertEquals(1, tspWithoutTimeWindows.getBestSolution(1).intValue());
		assertEquals(4, tspWithoutTimeWindows.getBestSolution(2).intValue());
		assertEquals(2, tspWithoutTimeWindows.getBestSolution(3).intValue());
		assertEquals(5, tspWithoutTimeWindows.getBestSolution(4).intValue());
		assertEquals(3, tspWithoutTimeWindows.getBestSolution(5).intValue());

		assertEquals(0, tspWithTimeWindows.getBestSolution(0).intValue());
		assertEquals(3, tspWithTimeWindows.getBestSolution(1).intValue());
		assertEquals(5, tspWithTimeWindows.getBestSolution(2).intValue());
		assertEquals(4, tspWithTimeWindows.getBestSolution(3).intValue());
		assertEquals(1, tspWithTimeWindows.getBestSolution(4).intValue());
		assertEquals(2, tspWithTimeWindows.getBestSolution(5).intValue());
	}

	/**
	 * Test method for {@link tsp.TSP#getCostBestSolution()}.
	 */
	@Test
	public void testGetCostBestSolution() {
		int costsSumWithoutTimeWindow = 0;
		int costsSumWithTimeWindow = 0;

		for (int i = 0; i < costs.length; i++) {
			costsSumWithoutTimeWindow += durations[i];
		}
		costsSumWithTimeWindow += costsSumWithoutTimeWindow;

		costsSumWithoutTimeWindow += 8 + 7 + 9 + 6 + 7 + 8;
		costsSumWithTimeWindow += 7 + 10 + 10 + 2 + 9 + 1 + 8 + 4;
		assertEquals(costsSumWithoutTimeWindow, tspWithoutTimeWindows.getCostBestSolution());
		assertEquals(costsSumWithTimeWindow, tspWithTimeWindows.getCostBestSolution());
	}

	/**
	 * Test method for {@link tsp.TSP#solutionFound()}.
	 */
	@Test
	public void testSolutionFound() {
		assertTrue(tspWithoutTimeWindows.solutionFound());
		assertTrue(tspWithTimeWindows.solutionFound());
		assertTrue(!impossibleTspWithTimeWindows.solutionFound());
	}
}
