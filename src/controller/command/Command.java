
package controller.command;

/**
 * @brief Definition of a command class
 * 
 * This interface will be implmented by each class that correspond to a command that can be done and undone in the program.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public interface Command {
	
	/**
	 * @brief DO the command
	 * 
	 * This method has to be called when the command is run for the first time, or when it is redone.
	 * 
	 * @except AlreadyDoneException In case the command has already been (re)done, it will throw an exception
	 */
	public void doCmd() throws AlreadyDoneException;
	
	/**
	 * @brief UNDO the command
	 * 
	 * This method has to be called when the command is undone.
	 * 
	 * It performs the opposite actions of @c doCmd().
	 * 
	 * @except AlreadyDoneException In case the command has already been undone, it will throw an exception
	 */
	public void undoCmd() throws AlreadyDoneException;
	
}
