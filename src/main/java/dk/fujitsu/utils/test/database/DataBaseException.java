package dk.fujitsu.utils.test.database;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseException extends RuntimeException {
    public DataBaseException() {
        super();
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
