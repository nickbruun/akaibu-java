package net.baconized.akaibu;

/**
 * Akaibu exception.
 *
 * Base exception for all Akaibu-related exceptions.
 */
public abstract class AkaibuException extends Exception {
    public AkaibuException(String message) {
        super(message);
    }
}
