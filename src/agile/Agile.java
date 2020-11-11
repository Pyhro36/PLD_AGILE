package agile;

import controller.Controller;
import model.OMap;

/**
 * Main class of the program
 * @author Joachim DOREL
 *
 */
public class Agile {
	
	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		OMap omap = new OMap();			
		new Controller(omap);
	}
}
