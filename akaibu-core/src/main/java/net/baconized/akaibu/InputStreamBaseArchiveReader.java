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
        // Read the size prefix.
        int prefix = _stream.read();
        if (prefix == -1)
            return null;

        // Determine the size.
        long size;

        if ((prefix & 0b10000000) == 0) {
            // One octet size indicator.
            size = (long)(prefix & 0b01111111);
        }
        else if ((prefix & 0b11000000) == 0b10000000) {
            // Two octet size indicator.
            byte[] suffix = new byte[1];
            IOUtils.readFully(_stream, suffix);
            size =
                ((long)(prefix & 0b00111111) << 8) |
                (long)(suffix[0]);
        }
        else if ((prefix & 0b11100000) == 0b11000000) {
            // Three octet size indicator.
            byte[] suffix = new byte[2];
            IOUtils.readFully(_stream, suffix);
            size =
                ((long)(prefix & 0b00011111) << 16) |
                ((long)(suffix[0]) << 8) |
                (long)(suffix[1]);
        }
        else if ((prefix & 0b11110000) == 0b11100000) {
            // Four octet size indicator.
            byte[] suffix = new byte[3];
            IOUtils.readFully(_stream, suffix);
            size =
                ((long)(prefix & 0b00001111) << 24) |
                ((long)(suffix[0]) << 16) |
                ((long)(suffix[1]) << 8) |
                (long)(suffix[2]);
        }
        else if ((prefix & 0b11111000) == 0b11110000) {
            // Five octet size indicator.
            byte[] suffix = new byte[4];
            IOUtils.readFully(_stream, suffix);
            size =
                ((long)(prefix & 0b00000111) << 32) |
                ((long)(suffix[0]) << 24) |
                ((long)(suffix[1]) << 16) |
                ((long)(suffix[2]) << 8) |
                (long)(suffix[3]);
        }
        else
            throw new InvalidArchiveException(
                String.format("invalid record size prefix: %d", prefix)
            );

        // Read the record data.
        if (size > Integer.MAX_VALUE)
            throw new RecordTooLargeException(String.format(
                "record is greater than supported size in Java: %d", size
            ));

        byte[] data = new byte[(int)size];
        IOUtils.readFully(_stream, data);
        return data;
    }
}
