package view;

import java.awt.GridLayout;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.Controller;
import model.OMap;
//import view.EcouteurDeClavier;
//import view.EcouteurDeSouris;
import view.ButtonsListener;

/**
 * Class of the window containing the graphical view
 * @author Joachim
 *
 */
@SuppressWarnings("serial")
public class Window extends JFrame{

	public final static String OPEN_MAP = "Open Map";
	public final static String ADD_ORDER = "Add Order";
	public final static String CALCULATE_ORDER = "Calculate Order";
	public final static String EXPORT_ORDER = "Export the Order"; //Gere par Victoire Chapelle
	public final static String UNDO = "Undo"; //Gere par Thibaut
	public final static String REDO = "Redo"; // Gere par thibaut
	public final static String CHANGE_ORDER_UP = "Move Delivery Up";
	public final static String CHANGE_ORDER_DOWN = "Move Delivery Down";
	public final static String ADD_DELIVERY = "Add Delivery";
	public final static String REMOVE_DELIVERY = "Remove Delivery";
	public final static String STOP = "Stop"; //Gere par PL Lefebvre
	
	//Classes dépendantes d'autres classes
	private GraphicalView graphicalView;
	private TextualView textualView;
	private ButtonsListener buttonsListener;
	private MouseListener mouseListener;
	//private EcouteurDeClavier ecouteurDeClavier;
	
	private ArrayList<JButton> buttons;
	private static JLabel messagesBox;
	
	private final String[] buttonsName = new String[]{OPEN_MAP, ADD_ORDER, CALCULATE_ORDER, EXPORT_ORDER, 
			UNDO, REDO, CHANGE_ORDER_UP, CHANGE_ORDER_DOWN, ADD_DELIVERY, REMOVE_DELIVERY, STOP};
	private final int buttonHeight = 40;
	private final int buttonWidth = 200;
	private final int messagesBoxHeight = 80;
	private final int textualViewWidth = 400;
	
	/**
	 * Constructor of the window
	 * @param omap			omap containing all the model
	 * @param scale			scale of the window
	 * @param controller	controller of the software
	 */
	public Window(OMap omap, int scale , Controller controller) {
		setLayout(null);
		createButtons(controller);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
		messagesBox = new JLabel();
		messagesBox.setBorder(BorderFactory.createTitledBorder("Messages..."));
		getContentPane().add(messagesBox);
		graphicalView = new GraphicalView(omap, 1, this);
		textualView = new TextualView(omap, this);
		mouseListener = new MouseListener(controller,graphicalView,this);
		addMouseListener(mouseListener);
		//ecouteurDeClavier = new EcouteurDeClavier(controleur);
		//addKeyListener(ecouteurDeClavier);
		setWindowSize();
		setVisible(true);
	}
	
	/**
	 * Method to add buttons to the window
	 * @param controller	controller listening to the interactions on the window
	 */
	private void createButtons(Controller controller){
		buttonsListener = new ButtonsListener(controller, controller.getMap());
		buttons = new ArrayList<JButton>();
		for (String buttonName : buttonsName){
			JButton button = new JButton(buttonName);
			buttons.add(button);
			button.setSize(buttonWidth,buttonHeight);
			button.setLocation(0,(buttons.size()-1)*buttonHeight);
			button.setFocusable(false);
			button.setFocusPainted(false);
			button.addActionListener(buttonsListener);
			if(!buttonName.contentEquals(OPEN_MAP))button.setEnabled(false);
			getContentPane().add(button);	
		}
	}
	
	/**
	 * Method to set the size of the window, using the informations that we know about
	 * the graphical elements on the window
	 */
	private void setWindowSize() {
		int buttonsHeight = buttonHeight*buttonsName.length;
		int windowHeight = Math.max(graphicalView.getHeight(),buttonsHeight)+messagesBoxHeight;
		int windowWidth = graphicalView.getWidth()+buttonWidth+textualViewWidth+10;
		setSize(windowWidth, windowHeight);
		messagesBox.setSize(windowWidth,60);
		messagesBox.setLocation(0,windowHeight-messagesBoxHeight);
		//vueGraphique.setLocation(largeurBouton, 0);
		graphicalView.setSize(windowWidth-buttonWidth-30, windowHeight-messagesBoxHeight);
		graphicalView.setLocation(buttonWidth, 0);
		textualView.setSize(textualViewWidth,windowHeight-messagesBoxHeight);
		textualView.setLocation(10+graphicalView.getWidth()+buttonWidth, 0);
	}
	
	/**
	 * Method to display a message in the message box
	 * @param message	message that the user want to display
	 */
	public void displayMessage(String message) {
		messagesBox.setText(message);
	}
	
	/**
	 * 	For a the dev who will work on the part of the code, you should try to
	 * 	create a unique method to handle the buttons 
	 */
	
	/**
	 * Method to disallow all the buttons when a new map is loaded
	 * @param b				boolean allowing or disallowing buttons
	 */
	public void allowButtons(Boolean b) {
		for(JButton button : buttons) {
			if(!button.getText().equals("Open Map"))
			button.setEnabled(b);
		}
	}
	
	/**
	 * Method to allow new buttons when the map is loaded
	 * @param b				boolean allowing or disallowing buttons
	 * @param buttonName	text written on the button that we want to allow
	 */
	public void allowButtons(Boolean b, String buttonName) {
		for(JButton button : buttons) {
			if(button.getText().equals(buttonName))
			button.setEnabled(b);
		}
	}
		
	/**
	 * Method to get the scale of the window
	 * @return	an int corresponding to the scale of the button
	 */
	public int getScale(){
		//return graphicalView.scale;
		return 0;
	}
	
	/**
	 * Method to change the scale of the window
	 * @param scale	the new scale that we want to use for the window
	 */
	public void setScale(int scale){
		//graphicalView.setScale(scale);
		setWindowSize();
	}
	
	public static void open_AddDelivery(Controller controller){
		
		Format timeFormat = new SimpleDateFormat("HH:mm:ss");
		int start=0, end=0, duration=0;

		JFormattedTextField DeliveryWindowStart = new JFormattedTextField(timeFormat);
		DeliveryWindowStart.setToolTipText("HH:mm:ss");
		JFormattedTextField DeliveryWindowEnd = new JFormattedTextField(timeFormat);
		DeliveryWindowEnd.setToolTipText("HH:mm:ss");
		JFormattedTextField DeliveryDuration = new JFormattedTextField(timeFormat);
		DeliveryDuration.setToolTipText("HH:mm:ss");
		
	    JPanel panelAddDelivery = new JPanel(new GridLayout(3, 3));
		
	    panelAddDelivery.add(new JLabel("Start of the Time Window : "));
	    panelAddDelivery.add(DeliveryWindowStart);
	    panelAddDelivery.add(new JLabel("in HH:mm:ss"));
	    panelAddDelivery.add(new JLabel("End of the Time Window : "));
	    panelAddDelivery.add(DeliveryWindowEnd);
	    panelAddDelivery.add(new JLabel("in HH:mm:ss"));
	    panelAddDelivery.add(new JLabel("Delivery duration : "));
	    panelAddDelivery.add(DeliveryDuration);
	    panelAddDelivery.add(new JLabel("in HH:mm:ss"));

	    int result = JOptionPane.showConfirmDialog(null, panelAddDelivery, 
	               "Add New Delivery", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	start=parseTime(DeliveryWindowStart.getText());
	    	end=parseTime(DeliveryWindowEnd.getText());
	    	duration=parseTime(DeliveryDuration.getText());
	    }
	    if(start+duration>end){
	    	//Write in messageBox : les temps ne correspondent pas
	    }else{
	    	if(controller.getMap().getSelectedIntersection() != null){
		    	controller.addDelivery(controller.getMap().getSelectedIntersection().getId(), start, end, duration, controller.getMap().getDeliveryOrder().getDeliveries().size());
	    	} else {
	    		//displayMessage("The intersection is not selected.");
	    	}
	    }
	}
	
	private static int parseTime(String time){
		return Integer.parseInt(time.split("[:]")[0])*3600
				+Integer.parseInt(time.split("[:]")[1])*60
				+Integer.parseInt(time.split("[:]")[2]);
	}
	
	public void clearTextualView(){
		textualView.clear();
	}
}
