package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Input stream unpacker.
 */
public final class InputStreamUnpacker {
    /**
     * Unpack integer.
     */
    public static final Long unpackInt(InputStream stream)
        throws IOException, InvalidArchiveException {
        int prefix = stream.read();
        if (prefix == -1)
            return null;

        if ((prefix & 0b10000000) == 0) {
            // One octet size indicator.
            return (long)(prefix & 0b01111111);
        }
        else if ((prefix & 0b11000000) == 0b10000000) {
            // Two octet size indicator.
            byte[] suffix = new byte[1];
            IOUtils.readFully(stream, suffix);
            return
                ((long)(prefix & 0b00111111) << 8) |
                (long)(suffix[0] & 0xff);
        }
        else if ((prefix & 0b11100000) == 0b11000000) {
            // Three octet size indicator.
            byte[] suffix = new byte[2];
            IOUtils.readFully(stream, suffix);
            return
                ((long)(prefix & 0b00011111) << 16) |
                ((long)(suffix[0] & 0xff) << 8) |
                (long)(suffix[1] & 0xff);
        }
        else if ((prefix & 0b11110000) == 0b11100000) {
            // Four octet size indicator.
            byte[] suffix = new byte[3];
            IOUtils.readFully(stream, suffix);
            return
                ((long)(prefix & 0b00001111) << 24) |
                ((long)(suffix[0] & 0xff) << 16) |
                ((long)(suffix[1] & 0xff) << 8) |
                (long)(suffix[2] & 0xff);
        }
        else if ((prefix & 0b11111000) == 0b11110000) {
            // Five octet size indicator.
            byte[] suffix = new byte[4];
            IOUtils.readFully(stream, suffix);
            return
                ((long)(prefix & 0b00000111) << 32) |
                ((long)(suffix[0] & 0xff) << 24) |
                ((long)(suffix[1] & 0xff) << 16) |
                ((long)(suffix[2] & 0xff) << 8) |
                (long)(suffix[3] & 0xff);
        }
        else
            throw new InvalidArchiveException(
                String.format("invalid record size prefix: %d", prefix)
            );
    }
}
