package net.baconized.akaibu;

/**
 * Unsupported archive version exception.
 */
public class UnsupportedVersionException extends AkaibuException {
    public UnsupportedVersionException(String message) {
        super(message);
    }
}
