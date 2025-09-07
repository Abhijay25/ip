package quacker;

/**
 * Checked exception used to signal for user errors
 */

public class QuackerException extends Exception {

    public QuackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuackerException(String message) {
        super(message);
    }
}