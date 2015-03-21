package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

import org.xerial.snappy.SnappyFramedInputStream;

/**
 * Snappy-compressed archive input stream reader.
 */
public class InputStreamSnappyCompressedArchiveReader
    extends InputStreamBaseArchiveReader {
    /**
     * Initialize a new Snappy-compressed archive input stream reader.
     *
     * Do not instantiate this class directly. Use
     * {@link InputStreamArchiveReaderFactory#create(InputStream)} instead.
     *
     * Expects the header of the archive to have been read.
     *
     * @param stream Stream.
     * @throws IOException if an I/O error occurs setting up the decompression
     * stream.
     */
    public InputStreamSnappyCompressedArchiveReader(InputStream stream)
        throws IOException {
        super(new SnappyFramedInputStream(stream));
    }
}
