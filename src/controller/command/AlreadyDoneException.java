package controller.command;

/**
 * @brief The command cannot be performed
 * 
 * This exception is thrown whenever an action is asked, but cannot be performed because it has already been performed.
 * 
 * This happens when you try to redo an action that has not be undone, or to undo an action that has already be undone.
 * 
 * If the action cannot be done because another reason blocks it, this exception is not to be used.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class AlreadyDoneException extends RuntimeException {

	private static final long serialVersionUID = -8509602712017708532L;

	/**
	 * @brief Custom constructor
	 * 
	 * This constructor generates a basic message using the name of the command and whether the command has been done or undone.
	 * 
	 * @param command The name of the command that generated the error
	 * @param done @c true if the user tried to do a done command, @c false if he tried to undo an undone command
	 */
	AlreadyDoneException(String command, boolean done) {
		super("The command " + command + " has already been " + (done ? "done" : "undone") + ".");
	}
}
