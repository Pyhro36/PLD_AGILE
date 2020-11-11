package controller.command;

import model.Delivery;
import model.OMap;

/**
 * @brief Command to add a delivery
 * 
 * This class creates a delivery and adds it to the delivery list of the model.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class AddDeliveryCommand implements Command {

	///@brief Has the command already been done
	private boolean done = false;
	
	private Delivery delivery;
	private OMap model;
	private int index;
	
	public AddDeliveryCommand(int intersectionID, int timeBegin, int timeEnd, int duration, int index, OMap model) {
		this.delivery = new Delivery(model.getIntersections().get(intersectionID), duration);
		this.model = model;
		this.index = index;
	}
	
	public AddDeliveryCommand(Delivery delivery, int index, OMap model){
		this.delivery = delivery;
		this.model = model;
		this.index = index;
	}

	@Override
	public void doCmd() throws AlreadyDoneException {
		if (done) {
			throw new AlreadyDoneException("Add Delivery", true);
		}
		this.model.addDeliveryToDeliveryRound(this.delivery, this.index);
		this.done = true;
		//this.model.update();
	}

	@Override
	public void undoCmd() throws AlreadyDoneException {
		if (!done) {
			throw new AlreadyDoneException("Add Delivery", false);
		}
		this.model.deleteDeliveryToDeliveryRound(this.delivery, this.index);
		this.done = false;
		//this.model.update();
	}

}
