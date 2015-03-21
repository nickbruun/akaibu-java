package net.baconized.akaibu;

/**
 * Archive fixtures.
 */
public class ArchiveFixtures {
    /**
     * Uncompressed header for format specification version 1.
     */
    public final static byte[] v1UncompressedHeader = new byte[] {
        'A', 'K', 'A', 'I', 1, 0, 0, 0,
    };

    /**
     * Header for version 2.
     */
    public final static byte[] v2Header = new byte[] {
        'A', 'K', 'A', 'I', 2, 0, 0, 0,
    };

    /**
     * Header for format specification version 1 with invalid compression.
     */
    public final static byte[] v1InvalidCompressionHeader = new byte[] {
        'A', 'K', 'A', 'I', 1, 3, 0, 0,
    };
}
