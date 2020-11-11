package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;
import model.OMap;
import view.Window;

/**
 * Class used to listen the action performed on the buttons
 * @author Joachim
 *
 */
public class ButtonsListener implements ActionListener{

	private Controller controller;
	private OMap model;

	/**
	 * Constructor to connect the controller
	 * @param controller controller of the window
	 */
	public ButtonsListener(Controller controller, OMap model){
		this.controller = controller;
		this.model = model;
	}

	/**
	 * Method to analyze the actions performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) { 
		switch (e.getActionCommand()){
		case Window.OPEN_MAP: controller.openMap(); break;
		case Window.ADD_ORDER: controller.addOrder(); break;
		case Window.CALCULATE_ORDER: controller.computeOrder(); break;
		case Window.STOP: controller.stop(); break;
		case Window.EXPORT_ORDER: controller.exportOrder(); break;
		case Window.ADD_DELIVERY: Window.open_AddDelivery(controller); break;
		case Window.CHANGE_ORDER_UP: controller.moveDelivery(model.getSelectedDeliveryIndex(), true); break;
		case Window.CHANGE_ORDER_DOWN: controller.moveDelivery(model.getSelectedDeliveryIndex(), false); break;
		case Window.REMOVE_DELIVERY: controller.deleteDelivery(model.getSelectedDeliveryIndex()); break;
		case Window.UNDO: controller.undo(); break;
		case Window.REDO: controller.redo(); break;

		}
	}
}
