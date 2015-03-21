package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

/**
 * Uncompressed archive input stream reader.
 */
public class InputStreamUncompressedArchiveReader
    extends InputStreamBaseArchiveReader {
    /**
     * Initialize a new uncompressed archive input stream reader.
     *
     * Do not instantiate this class directly. Use
     * {@link InputStreamArchiveReaderFactory#create(InputStream)} instead.
     *
     * Expects the header of the archive to have been read.
     *
     * @param stream Stream.
     */
    public InputStreamUncompressedArchiveReader(InputStream stream) {
        super(stream instanceof BufferedInputStream ?
              stream :
              new BufferedInputStream(stream));
    }
}
