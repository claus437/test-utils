package dk.fujitsu.utils.test.table.tableassertionrunnersupport;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-09-11
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class IllegalPositionException extends RuntimeException {
    public IllegalPositionException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IllegalPositionException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IllegalPositionException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IllegalPositionException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
