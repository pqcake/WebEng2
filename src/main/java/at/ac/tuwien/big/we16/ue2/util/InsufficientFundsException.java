package at.ac.tuwien.big.we16.ue2.util;

/**
 * Created by pqpies on 4/21/16.
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {

    }

    public InsufficientFundsException(String message) {
        super(message);

    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);

    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);

    }
}
