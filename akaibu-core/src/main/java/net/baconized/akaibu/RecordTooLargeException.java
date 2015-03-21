package net.baconized.akaibu;

/**
 * Record too large exception.
 *
 * Thrown if the size of a record is greater than can be allocated in Java.
 */
public class RecordTooLargeException extends AkaibuException {
    public RecordTooLargeException(String message) {
        super(message);
    }
}

