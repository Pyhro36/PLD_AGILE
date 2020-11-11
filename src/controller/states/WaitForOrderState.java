/**
 * 
 */
package controller.states;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.Controller;
import view.Window;
import xml.Deserializer;
import xml.ExceptionXML;

/**
 * @brief The application is waiting for an order file to be loaded
 * 
 * This state is active when no delivery order file has been loaded.
 * Typically, this happens just after a street map has been loaded.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class WaitForOrderState extends WaitForMapState {

	protected static final String PROMPT_MESSAGE = "Click on \"Add order\" to begin";
	protected static final String ERROR_MESSAGE = "Unable to open order file. Please choose another file.";
	protected static final String FATAL_MESSAGE = "Fatal error while opening the file. Please refer to error console.";
	
	/**
	 * @brief Base constructor for this state class
	 * 
	 * This constructor only prints a message in the message area.
	 * No other Entry action exists for this state.
	 * 
	 * @param controller A reference to the application controller
	 */
	public WaitForOrderState(Controller controller) {
		super(controller);
		this.controller.getWindow().displayMessage(PROMPT_MESSAGE);
	}
		
	/**
	 * @brief Opens an order XML file
	 * 
	 * This method is to be called by the controller when the user clicks the "Add Order" button.
	 */
	public void addOrder() {
		try {
			Deserializer.load(this.controller.getMap());
			try{
				this.controller.getMap().update();
				this.controller.getWindow().allowButtons(true, Window.CALCULATE_ORDER);
			}catch(Exception e){
				this.controller.getWindow().displayMessage(FATAL_MESSAGE + " \n" + e.getMessage());
				e.printStackTrace();
				return;
			}
		} catch (ParserConfigurationException e) {
			this.controller.getWindow().displayMessage(FATAL_MESSAGE + " \n" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (SAXException e) {
			this.controller.getWindow().displayMessage(FATAL_MESSAGE + " \n" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			this.controller.getWindow().displayMessage(FATAL_MESSAGE + " \n" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (ExceptionXML e) {
			this.controller.getWindow().displayMessage(ERROR_MESSAGE + " \n" + e.getMessage());
			//e.printStackTrace();
		}
		this.controller.setState(StatesEnum.FILES_LOADED);
	}
}
