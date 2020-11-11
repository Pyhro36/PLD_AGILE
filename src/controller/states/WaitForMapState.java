package controller.states;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.Controller;
import view.Window;
import xml.Deserializer;
import xml.ExceptionXML;

/**
 * @brief The application is waiting for a map file to be loaded
 * 
 * This state is active when no street map file has been loaded.
 * Typically, this happens at the start of the program.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class WaitForMapState extends DefaultState {

	protected static final String PROMPT_MESSAGE = "Click on \"Open a map\" to begin";
	protected static final String ERROR_MESSAGE = "Unable to open map file. Please choose another file.";
	protected static final String EMPTY_MESSAGE = "This map does not contain any street or intersection.";
	protected static final String FATAL_MESSAGE = "Fatal error while opening the file. Please refer to error console.";
	
	/**
	 * @brief Base constructor for this state class
	 * 
	 * This constructor only prints a message in the message area.
	 * No other Entry action exists for this state.
	 * 
	 * @param controller A reference to the application controller
	 */
	public WaitForMapState(Controller controller) {
		super(controller);
		this.controller.getWindow().displayMessage(PROMPT_MESSAGE);
	}
	
	/**
	 * @brief Opens a map XML file
	 * 
	 * This method is to be called by the controller when the user clicks the "Open Map" button.
	 */
	public void openMap() {
		this.controller.getMap().clean();
		this.controller.getWindow().allowButtons(false);
		try {
			Deserializer.load(this.controller.getMap());
			if(this.controller.getMap().getIntersections().size()!=0 && this.controller.getMap().getSections().size()!=0){
				this.controller.getMap().update();
				this.controller.getWindow().allowButtons(true, Window.ADD_ORDER);
			}else{
				this.controller.getMap().clean();
				this.controller.getWindow().displayMessage(ERROR_MESSAGE + " \n" + EMPTY_MESSAGE);
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
		this.controller.setState(StatesEnum.WAIT_FOR_ORDER);
	}

}
