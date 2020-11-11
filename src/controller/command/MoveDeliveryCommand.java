package controller.command;

import model.Delivery;
import model.OMap;

public class MoveDeliveryCommand implements Command {

	boolean done = false;
	
	private OMap model;
	private int deliveryIndex;
	private boolean up;
	
	public MoveDeliveryCommand(int deliveryIndex, boolean up, OMap model) {
		this.model = model;
		this.deliveryIndex = deliveryIndex;
		this.up = up;
	}

	@Override
	public void doCmd() throws AlreadyDoneException {
		if (done) {
			throw new AlreadyDoneException("Move Delivery " + (up? "up" : "down"), true);
		}
		Delivery d = model.getDeliveryFromDeliveryRoundByIndex(deliveryIndex);
		model.deleteDeliveryToDeliveryRound(d, deliveryIndex);
		model.addDeliveryToDeliveryRound(d, deliveryIndex + (up? -1 : +1));
		this.done = true;
		//model.update();
	}

	@Override
	public void undoCmd() throws AlreadyDoneException {
		if (!done) {
			throw new AlreadyDoneException("Move Delivery " + (up? "up" : "down"), true);
		}
		Delivery d = model.getDeliveryFromDeliveryRoundByIndex(deliveryIndex + (up? -1 : +1));
		model.deleteDeliveryToDeliveryRound(d, deliveryIndex + (up? -1 : +1));
		model.addDeliveryToDeliveryRound(d, deliveryIndex);
		this.done = false;
		//model.update();
	}

}
