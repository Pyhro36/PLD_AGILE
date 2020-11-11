package tests;

import model.Hour;
import model.OMap;
import shortestpathalgo.Graph;

/**
 * Abstract class for a creation of graphs samples for the tests
 * 
 * @author Pierre-Louis
 *
 */
public abstract class GraphFactoryForTest {

	private static OMap oMap;
	private static int[][] costs;
	private static int[] durations;
	private static Hour[] timeWindowsStarts;
	private static Hour[] timeWindowsEnds;
	private static Hour[] impossibleTimeWindowsEnds;
	private static Hour departureTime;
	private static boolean tspSampleCreated = false;

	/**
	 * @return a graph sample implemented on OMap class model
	 */
	public static Graph getOMapGraphSample() {
		if (oMap == null) {
			oMap = new OMap();
			oMap.addIntersection(0, 0, 0);
			oMap.addIntersection(0, 0, 1);
			oMap.addIntersection(0, 0, 2);
			oMap.addIntersection(0, 0, 3);
			oMap.addIntersection(0, 0, 4);
			oMap.addSection(0, 1, 3, 1, "");
			oMap.addSection(0, 4, 5, 1, "");
			oMap.addSection(1, 2, 7, 1, "");
			oMap.addSection(1, 4, 2, 1, "");
			oMap.addSection(2, 3, 2, 1, "");
			oMap.addSection(3, 0, 3, 1, "");
			oMap.addSection(3, 2, 7, 1, "");
			oMap.addSection(4, 1, 1, 1, "");
			oMap.addSection(4, 2, 4, 1, "");
			oMap.addSection(4, 3, 6, 1, "");
		}

		return oMap;
	}

	/**
	 * @return a sample of costs based on the PLD Agile Part 5 for the TSP tests
	 */
	public static int[][] getSampleCosts() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return costs;
	}

	/**
	 * @return a sample of durations based on the PLD Agile Part 5 for the TSP
	 *         tests
	 */
	public static int[] getSampleDurations() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return durations;
	}

	/**
	 * @return a sample of min border of time window for the TSP tests
	 */
	public static Hour[] getSampleTimeWindowMin() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return timeWindowsStarts;
	}

	/**
	 * @return a sample of valid max border of time window for the TSP tests
	 */
	public static Hour[] getSampleTimeWindowMaxValid() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return timeWindowsEnds;
	}

	/**
	 * @return a sample of max border of time window for the TSP tests such as
	 *         the TSP be impossible
	 */
	public static Hour[] getImpossibleTimeWindowMax() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return impossibleTimeWindowsEnds;
	}

	/**
	 * @return the departure time for the TSP tests with time windows
	 */
	public static Hour getDepartureTime() {
		if (!tspSampleCreated) {
			createElementsForTSPSample();
		}

		return departureTime;
	}

	/**
	 * prepare the elements for TSP tests, based on PLD Agile Part 5
	 */
	private static void createElementsForTSPSample() {
		costs = new int[6][6];
		durations = new int[6];
		timeWindowsStarts = new Hour[6];
		timeWindowsEnds = new Hour[6];
		impossibleTimeWindowsEnds = new Hour[6];

		departureTime = new Hour(0, 0, 0);

		durations[0] = 0;
		durations[1] = 4;
		durations[2] = 2;
		durations[3] = 3;
		durations[4] = 4;
		durations[5] = 3;

		timeWindowsStarts[3] = new Hour(0, 0, 7); // 7
		timeWindowsEnds[3] = new Hour(0, 0, 11); // 10

		timeWindowsStarts[5] = new Hour(0, 0, 19); // 20
		timeWindowsEnds[5] = new Hour(0, 0, 23); // 23

		timeWindowsStarts[4] = new Hour(0, 0, 35); // 33 + 2 = 35
		timeWindowsEnds[4] = new Hour(0, 0, 40); // 39

		timeWindowsStarts[1] = new Hour(0, 0, 49); // 48 + 1 = 49
		timeWindowsEnds[1] = new Hour(0, 0, 54); // 53

		timeWindowsStarts[2] = new Hour(0, 1, 0); // 1:1
		timeWindowsEnds[2] = new Hour(0, 1, 4); // 1:3

		// 1:7

		impossibleTimeWindowsEnds[3] = new Hour(0, 0, 9);

		costs[0][1] = 8;
		costs[0][2] = 5;
		costs[0][3] = 7;
		costs[0][4] = 10;
		costs[0][5] = 23;
		costs[1][0] = 5;
		costs[1][2] = 8;
		costs[1][3] = 13;
		costs[1][4] = 7;
		costs[1][5] = 17;
		costs[2][0] = 4;
		costs[2][1] = 12;
		costs[2][3] = 8;
		costs[2][4] = 9;
		costs[2][5] = 6;
		costs[3][0] = 8;
		costs[3][1] = 10;
		costs[3][2] = 12;
		costs[3][4] = 17;
		costs[3][5] = 10;
		costs[4][0] = 13;
		costs[4][1] = 9;
		costs[4][2] = 9;
		costs[4][3] = 20;
		costs[4][5] = 11;
		costs[5][0] = 19;
		costs[5][1] = 14;
		costs[5][2] = 9;
		costs[5][3] = 7;
		costs[5][4] = 10;

		tspSampleCreated = true;
	}
}
