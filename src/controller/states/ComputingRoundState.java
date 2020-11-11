package controller.states;

import controller.Controller;
import view.Window;

/**
 * @brief the application is computing solutions for the delivery round
 * 
 *        in this state, a second thread is running to compute round
 * 
 * @author Pierre-Louis Lefebvre
 *
 */

public class ComputingRoundState extends FilesLoadedState {

	protected static final String ENTRY_MESSAGE = "Research of delivery round, click on Stop Button to stop the comuting if the round seems satisfying";
	protected static final String IMPOSSIBLE_ROUND_MESSAGE = "Unable to compute a delivery round, the time windows are impossible to respect.";

	private Thread waitingThread;

	/**
	 * @brief Base constructor for this state class
	 * 
	 *        This constructor prints a message in the message area and starts
	 *        the thread of computing
	 * 
	 * @param controller
	 *            A reference to the application controller
	 */
	public ComputingRoundState(Controller controller) {
		super(controller);
		this.controller.getWindow().displayMessage(ENTRY_MESSAGE);

		Thread computeThread;
		computeThread = new Thread(new ComputeRun());
		waitingThread = new Thread(new WaitRun(computeThread));
		computeThread.start();
		waitingThread.start();

		this.controller.getWindow().allowButtons(true, Window.STOP);
		this.controller.getWindow().allowButtons(false, Window.CALCULATE_ORDER);
	}

	public void stopComputing() {
		this.controller.getMap().stopComuting();

		if (this.controller.getMap().solutionFound()) {
			this.controller.setState(StatesEnum.ROUND_COMPUTED);

		} else {
			this.controller.getWindow().displayMessage(IMPOSSIBLE_ROUND_MESSAGE);
		}
	}

	/**
	 * @brief Class that define the computing of the Round in a separate Thread
	 * 
	 * @author Pierre-Louis
	 */
	private class ComputeRun implements Runnable {

		@Override
		public void run() {
			controller.getMap().computeRound();
		}
	}

	/**
	 * @brief Class that define the run method which waits the end of the
	 *        Comuting Thread to notify the controller
	 * 
	 * @author Pierre-Louis
	 */
	private class WaitRun implements Runnable {

		private Thread waitedThread;

		public WaitRun(Thread waitedThread) {
			this.waitedThread = waitedThread;
		}

		@Override
		public void run() {
			try {
				waitedThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stopComputing();
		}
	}
}
