package at.ac.tuwien.big.we16.ue2.util;

/**
 * Created by pqpies on 4/21/16.
 */
public class InsufficientAmountException extends Exception {
    public InsufficientAmountException() {

    }

    public InsufficientAmountException(String message) {
        super(message);

    }

    public InsufficientAmountException(Throwable cause) {
        super(cause);

    }

    public InsufficientAmountException(String message, Throwable cause) {
        super(message, cause);

    }
}
