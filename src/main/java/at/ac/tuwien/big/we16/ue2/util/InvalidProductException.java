package at.ac.tuwien.big.we16.ue2.util;

/**
 * Created by pqpies on 4/21/16.
 */
public class InvalidProductException extends Exception {
    public InvalidProductException() {

    }

    public InvalidProductException(String message) {
        super(message);

    }

    public InvalidProductException(Throwable cause) {
        super(cause);

    }

    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);

    }
}
