package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Base archive input stream reader.
 */
public abstract class InputStreamBaseArchiveReader implements InputStreamArchiveReader {
    /**
     * Input stream.
     */
    private InputStream _stream;

    /**
     * Initialize a new archive input stream reader.
     *
     * Expects the header of the archive to have been read.
     *
     * @param stream Stream.
     */
    public InputStreamBaseArchiveReader(InputStream stream) {
        _stream = stream;
    }

    public byte[] read() throws AkaibuException, IOException {
        // Determine the size.
        Long size = InputStreamUnpacker.unpackInt(_stream);
        if (size == null)
            return null;

        // Read the record data.
        if (size > Integer.MAX_VALUE)
            throw new RecordTooLargeException(String.format(
                "record is greater than supported size in Java: %d", size
            ));

        byte[] data = new byte[size.intValue()];
        IOUtils.readFully(_stream, data);
        return data;
    }
}
