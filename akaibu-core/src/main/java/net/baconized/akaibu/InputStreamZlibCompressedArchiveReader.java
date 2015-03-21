package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

import java.util.zip.InflaterInputStream;

/**
 * zlib-compressed archive input stream reader.
 */
public class InputStreamZlibCompressedArchiveReader
    extends InputStreamBaseArchiveReader {
    /**
     * Initialize a new zlib-compressed archive input stream reader.
     *
     * Do not instantiate this class directly. Use
     * {@link InputStreamArchiveReaderFactory#create(InputStream)} instead.
     *
     * Expects the header of the archive to have been read.
     *
     * @param stream Stream.
     */
    public InputStreamZlibCompressedArchiveReader(InputStream stream) {
        super(new InflaterInputStream(stream));
    }
}
