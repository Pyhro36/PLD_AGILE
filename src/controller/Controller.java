package controller;

import java.util.List;
import java.util.Map;

import controller.command.CommandList;
import controller.states.State;
import controller.states.StatesEnum;
import model.OMap;
import view.Window;
import view.Point;
import model.Delivery;
import model.DeliveryOrder;
import model.Intersection;

/**
 * @brief Controller class, using the MVC pattern
 * 
 * This is the controller for the application, that will be monitoring the state of the program.
 * The GUI will trigger its actions by calling its methods. The affective way these actions are performed
 * will be controlled by the implementations of the State interface.
 * 
 * @authors Joachim DOREL
 * @authors Thibaut FERNANDEZ
 *
 */
public class Controller{
	
	///@brief The GUI
	private final Window window;
	
	///@brief The model
	private final OMap omap;
	
	///@brief The command history
	private final CommandList cmdHistory;
	
	///@brief Implementation of State that will do the actions of the controller
	private State currentState;
	
	///@brief Default size for the window
	public static final int DEFAULT_WINDOW_SIZE = 500;
	
	
	/**
	 * @brief Base constructor
	 * 
	 * This constructor does all the basic initialization of the application:
	 * @li Linking the model to the controller
	 * @li Creating the GUI
	 * @li Setting the controller into the initial state
	 * 
	 * @param omap An instance of the OMap class, used as a link to the model
	 * @param size The size of the window that will be created, passed as-is to the Winodw constructor
	 */
	public Controller(OMap omap, int size){
		this.omap=omap;
		this.window=new Window(omap, size, this);
		this.cmdHistory = new CommandList(this);
		this.currentState = StatesEnum.startupState(this);
	}
	
	/**
	 * @brief Default size constructor
	 * 
	 * This constructor does all the basic initialization of the application:
	 * @li Linking the model to the controller
	 * @li Creating the GUI
	 * @li Setting the controller into the initial state
	 * 
	 * This constructor uses the default winodw size defined in a class constant.
	 * 
	 * @param omap An instance of the OMap class, used as a link to the model
	 */
	public Controller(OMap omap){
		this(omap, DEFAULT_WINDOW_SIZE);
	}
	
	/**
	 * @brief Default constructor
	 * 
	 * This constructor does all the basic initialization of the application:
	 * @li Creating the model
	 * @li Linking the model to the controller
	 * @li Creating the GUI
	 * @li Setting the controller into the initial state
	 * 
	 * This constructor uses the default winodw size defined in a class constant, and creates an OMap instance from scratch.
	 * 
	 * @param omap An instance of the OMap class, used as a link to the model
	 */
	public Controller(){
		this(new OMap(), DEFAULT_WINDOW_SIZE);
	}
	
	/**
	 * @brief Opens a street map from a file
	 * 
	 * This method calls the underlying State's openMap() method.
	 */
	public void openMap(){
		currentState.openMap();
	}
	
	/**
	 * @brief Opens a delivery order from a file
	 * 
	 * This method calls the underlying State's addOrder() method.
	 */
	public void addOrder(){
		currentState.addOrder();
	}
	
	public void exportOrder(){
		currentState.exportOrder();
	}
	
	/**
	 * @brief Calculate and display a delivery round
	 * 
	 * This method calls the underlying State's computeOrder() method.
	 */
	public void computeOrder(){
		currentState.computeOrder();
	}
	
	///@brief Returns a reference to the underlying Window instance
	///@return A reference to the Window instance used by the application
	public Window getWindow() {
		return this.window;
	}
	
	///@brief Returns a reference to the underlying OMap instance
	///@return A reference to the OMap instance used by the application
	public OMap getMap() {
		return this.omap;
	}
	
	///@brief Returns a reference to the underlying CommandList instance
	///@return A reference to the CommandList instance used by the application
	public CommandList getCmdHistory() {
		return this.cmdHistory;
	}
	
	///@brief Change the application state
	public void setState(StatesEnum state) {
		this.currentState = StatesEnum.makeState(state, this);
	}
	
	/**
	 * @brief Undo a command
	 */
	public void undo() {
		currentState.undo();
	}
	
	/**
	 * @brief Redo a command
	 */
	public void redo() {
		currentState.redo();
	}
	
	/**
	 * @brief Add a delivery to the list
	 * 
	 * @param intersectionID The ID number of the intersection to which the delivery will be made
	 * @param timeBegin The beginning timestamp for the delivery time frame
	 * @param timeEnd The ending timestamp for the delivery time frame
	 * @param duration The duration of the delivery stop
	 * @param index The position in the list at which the delivery will be inserted
	 */
	public void addDelivery(int intersectionID, int timeBegin, int timeEnd, int duration, int index) {
		currentState.addDelivery(intersectionID, timeBegin, timeEnd, duration, index);
	}
	
	/**
	 * @brief Remove a delivery from the list
	 * 
	 * @param deliveryIndex The index in the list of the delivery to remove
	 */
	public void deleteDelivery(int deliveryIndex) {
		currentState.deleteDelivery(deliveryIndex);
	}
	
	/**
	 * @brief Move a delivery in the list
	 * 
	 * Moving a delivery "up" means swapping it with the delivery that would be processed before.
	 * Its index in the underlying delivery list will be decreased.
	 * 
	 * Moving the delivery "down" will increase its index.
	 * 
	 * @param deliveryIndex The index in the list of the delivery to move
	 * @param up @c true if the delivery is moved up, @c false if it is moved down
	 */
	public void moveDelivery(int deliveryIndex, boolean up) {
		currentState.moveDelivery(deliveryIndex, up);
	}
	
	//Added by Eric
	
	/**
	 * Method to find if there is a delivery at a point p on the map
	 * @param p Point being looked at
	 */
	public void findDelivery(Point p)
	{
		if(omap != null && omap.getDeliveryOrder() != null)
		{
			omap.getIntersections();
			
			DeliveryOrder searching = omap.getDeliveryOrder();
			List <Delivery> deliveries = searching.getDeliveries();
			deliveries.toArray();
			
			int clickedX = p.getX();
			int clickedY = p.getY();
			int targetX;
			int targetY;
			
			boolean intersectionExist=false;
						
			for(Map.Entry<Integer, Intersection> intersection : omap.getIntersections().entrySet()){
				targetX = (int)(intersection.getValue().getX());
				targetY = (int)(intersection.getValue().getY());
								
				if((withinX(targetX,clickedX,10) && withinX(targetY,clickedY,10)))
				{
					intersectionExist=true;
					//Output done here, can be modified as required
					window.displayMessage("You selected the intersection : " + intersection.getValue().toString() + " " 
							+ " | X : " + Integer.toString(intersection.getValue().getX()) 
							+ " | Y : " +  Integer.toString(intersection.getValue().getY())
							+".");
					omap.setSelectedIntersection(intersection.getValue());
					break;
				}
			}
			if(!intersectionExist)
			{
				window.displayMessage("Not a delivery");
			}
		}
	}
	
	/**
	 * @brief stops the comuting of the round, when Stop Button clicked
	 */
	public void stop() {
		currentState.stopComputing();
	}
	
	/**
	 * Method used by findDelivery to check if the pixel clicked is within a 
	 * x*x radius of a delivery
	 * @param lookingAt	The delivery being checked
	 * @param input	The coordinate clicked by the user
	 * @param radius within which the point is being looked for
	 * @return	true if within three
	 */
	private boolean withinX(int lookingAt,int input,int x)
	{
		if(input > lookingAt)
		{
			if(input - lookingAt < x)
			{
				return true;
			}
		}
		else if(lookingAt - input < x)
		{
			return true;
		}
		return false;
	}
}
