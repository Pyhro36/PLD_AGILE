package xml;

/**
 * Class to handle the exception when reading an XML file
 * 
 * @author Joachim
 *
 */
public class ExceptionXML extends Exception {

	private static final long serialVersionUID = 7981113938213427813L;

	/**
	 * Constructor of the file
	 * 
	 * @param message
	 * 
	 */
	public ExceptionXML(String message) {
		super(message);
	}

}