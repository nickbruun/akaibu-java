package net.baconized.akaibu;

/**
 * Compression format.
 */
public enum Compression {
    /**
     * Uncompressed.
     */
    UNCOMPRESSED(0),

    /**
     * zlib compression.
     */
    ZLIB(1),

    /**
     * Snappy compression.
     */
    SNAPPY(2);

    private final int _value;

    private Compression(final int value) {
        _value = value;
    }

    /**
     * Get integer value representation for compression method.
     *
     * @return the integer value representation for the compression method.
     */
    public final int getValue() {
        return _value;
    }

    /**
     * Get compression format from integer value.
     *
     * @param value Integer value.
     * @return the compression format represented by the integer value.
     * @throws InvalidCompressionException if the integer value representation
     * does not represent a known compression method.
     */
    public static final Compression fromValue(final int value) throws InvalidCompressionException {
        switch (value) {
        case 0:
            return UNCOMPRESSED;
        case 1:
            return ZLIB;
        case 2:
            return SNAPPY;
        default:
            throw new InvalidCompressionException(String.format("invalid compression method: %d", value));
        }
    }
}
