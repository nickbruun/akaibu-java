package net.baconized.akaibu;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Input stream archive reader factory.
 *
 * Creates an archive reader for an input stream.
 */
public class InputStreamArchiveReaderFactory {
    /**
     * Get an input stream archive reader for an input stream.
     *
     * Parses the archive header and instantiates the appropriate archive
     * reader for the archive.
     *
     * @param stream Input stream to read the archive from.
     * @return the appropriate archive reader for the archive.
     * @throws InvalidCompressionException if the integer value representation
     * does not represent a known compression method.
     * @throws InvalidArchiveException if the archive header is invalid.
     * @throws UnsupportedVersionException if the archive version is not
     * supported.
     * @throws IOException if reading the header(s) of the archive fails.
     */
    public static InputStreamArchiveReader create(InputStream stream)
        throws InvalidCompressionException,
               InvalidArchiveException,
               UnsupportedVersionException,
               IOException {
        // Read the header from the input stream.
        byte[] header = new byte[8];
        IOUtils.readFully(stream, header);

        if ((header[0] != ArchiveFormat.magicHeader[0]) ||
            (header[1] != ArchiveFormat.magicHeader[1]) ||
            (header[2] != ArchiveFormat.magicHeader[2]) ||
            (header[3] != ArchiveFormat.magicHeader[3]))
            throw new InvalidArchiveException(
                "archive has invalid magic header"
            );

        if (header[4] != 1)
            throw new UnsupportedVersionException(String.format(
                "unsupported archive version: %d", header[4]
            ));

        Compression compression = Compression.fromValue(header[5]);

        // Instantiate an archive reader depending on the compression method.
        switch (compression) {
        case UNCOMPRESSED:
            return new InputStreamUncompressedArchiveReader(stream);

        case ZLIB:
            return new InputStreamZlibCompressedArchiveReader(stream);

        case SNAPPY:
            return new InputStreamSnappyCompressedArchiveReader(stream);

        default:
            throw new InvalidCompressionException(
                String.format("invalid compression method: %s", compression)
            );
        }
    }
}
