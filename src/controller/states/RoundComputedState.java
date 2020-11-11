package controller.states;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import controller.Controller;
import controller.command.AddDeliveryCommand;
import controller.command.AlreadyDoneException;
import controller.command.DeleteDeliveryCommand;
import controller.command.MoveDeliveryCommand;
import view.Window;
import xml.ExceptionXML;
import xml.Serializer;

public class RoundComputedState extends FilesLoadedState {

	protected static final String ENTRY_MESSAGE = "Delivery round computed successfully.\nProceed to modifications if needed, or generate the roadbook.";
	protected static final String EXPORT_ERROR = "Unspecified error while exporting the order. Please try again.";
	
	public RoundComputedState(Controller controller) {
		super(controller);
		this.controller.getWindow().displayMessage(ENTRY_MESSAGE);
		this.controller.getWindow().allowButtons(true, Window.EXPORT_ORDER);
		this.controller.getWindow().allowButtons(true, Window.CHANGE_ORDER_UP);
		this.controller.getWindow().allowButtons(true, Window.CHANGE_ORDER_DOWN);
		this.controller.getWindow().allowButtons(true, Window.REMOVE_DELIVERY);
		this.controller.getWindow().allowButtons(true, Window.ADD_DELIVERY);
		this.controller.getWindow().allowButtons(false, Window.STOP);
	}
	
	public void undo() {
		try {
			this.controller.getCmdHistory().undo();
		} catch (AlreadyDoneException e) {
			// Silenced exception
		}
		this.controller.getWindow().clearTextualView();
		this.controller.getMap().update();
	}
	
	public void redo() {
		try {
			this.controller.getCmdHistory().redo();
		} catch (AlreadyDoneException e) {
			// Silenced exception
		}
		this.controller.getWindow().clearTextualView();
		this.controller.getMap().update();
	}
	

	public void addDelivery(int intersectionID, int timeBegin, int timeEnd, int duration, int index) {
		this.controller.getCmdHistory().add(
				new AddDeliveryCommand(
						intersectionID,
						timeBegin,
						timeEnd,
						duration,
						index,
						this.controller.getMap()
						)
				);
		this.controller.getWindow().clearTextualView();
		this.controller.getMap().update();
	}
	

	public void deleteDelivery(int deliveryIndex) {
		this.controller.getCmdHistory().add(
				new DeleteDeliveryCommand(
						deliveryIndex,
						this.controller.getMap()
						)
				);
		this.controller.getWindow().clearTextualView();
		this.controller.getMap().update();

	}

	public void moveDelivery(int deliveryIndex, boolean up) {
		this.controller.getCmdHistory().add(
				new MoveDeliveryCommand(
						deliveryIndex,
						up,
						this.controller.getMap()
						)
				);
		this.controller.getWindow().clearTextualView();
		this.controller.getMap().update();
	}
	
	public void exportOrder() {
		try {
			Serializer.save(this.controller.getMap().getDeliveryRound());
		} catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException
				| ExceptionXML e) {
			this.controller.getWindow().displayMessage(EXPORT_ERROR);
			e.printStackTrace();
		}
	}

}
