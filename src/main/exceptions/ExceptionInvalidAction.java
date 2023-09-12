package exceptions;

public class ExceptionInvalidAction extends Exception {
    public ExceptionInvalidAction() {
        super("You can't do this action. Type \"help\" for a list of commands");
    }
}
