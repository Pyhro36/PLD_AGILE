package controller.command;

import model.Delivery;
import model.OMap;

public class DeleteDeliveryCommand implements Command {

	///@brief Has the command already been done
	private boolean done = false;
	
	private OMap model;
	private Delivery delivery;
	private int index;
	
	public DeleteDeliveryCommand(int deliveryIndex, OMap model) {
		this.model = model;
		this.index = deliveryIndex;
		this.delivery = this.model.getDeliveryFromDeliveryRoundByIndex(deliveryIndex);
	}

	@Override
	public void doCmd() throws AlreadyDoneException {
		if (done) {
			throw new AlreadyDoneException("Delete Delivery", false);
		}
		this.model.deleteDeliveryToDeliveryRound(this.delivery, this.index);
		this.done = true;
		//this.model.update();
	}

	@Override
	public void undoCmd() throws AlreadyDoneException {
		if (!done) {
			throw new AlreadyDoneException("Delete Delivery", false);
		}
		this.model.addDeliveryToDeliveryRound(this.delivery, this.index);
		this.done = false;
		//this.model.update();
	}

}
