package controller.command;

import java.util.List;
import java.util.Vector;

import controller.Controller;
import view.Window;

/**
 * @brief Undo/Redo manager
 * 
 * This class manages the Command pattern.
 * 
 * It does so by storing each command in a list, each undo/redo moves a cursor on that list.
 * 
 * This class will run the commands itself, everytime a command instance is provided, it should not be already run.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class CommandList {

	/**
	 * @brief Underlying list of commands
	 * 
	 * This attribute is the command history. It is implemented as a vector.
	 */
	private List<Command> lst;
	
	///@brief Index to keep track of our current position in the command list
	private int index;
	
	private final Controller controller;
	
	public CommandList(Controller controller) {
		this.controller = controller;
		this.index = 0;
		this.lst = new Vector<Command>();
	}
	
	/**
	 * @brief Add and execute a command
	 * 
	 * This method will run the command, and it will then append it to the underlying list.
	 * 
	 * If there are undone commands that have not been redone, they will be discarded.
	 * 
	 * @param cmd The command to run
	 */
	public void add(Command cmd) {
		if (index != lst.size() && this.index != 0) {
			// We have undone commands that have not been redone
			// So we discard them.
			lst.removeAll(lst.subList(index, lst.size()));
		}
		cmd.doCmd();
		lst.add(cmd);
		this.index++;
		this.controller.getWindow().allowButtons(false, Window.REDO);
		this.controller.getWindow().allowButtons(true, Window.UNDO);
	}
	
	/**
	 * @brief Undoes a command
	 */
	public void undo() {
		lst.get(--index).undoCmd();
		if (this.index == 0) {
			this.controller.getWindow().allowButtons(false, Window.UNDO);
		}
		this.controller.getWindow().allowButtons(true, Window.REDO);
	}
	
	/**
	 * @brief Redoes a command
	 */
	public void redo() {
		lst.get(index++).doCmd();
		if (this.index == lst.size()) {
			this.controller.getWindow().allowButtons(false, Window.REDO);
		}
		this.controller.getWindow().allowButtons(true, Window.UNDO);
	}
}
