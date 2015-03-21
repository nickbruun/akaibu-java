package net.baconized.akaibu;

import java.io.IOException;

/**
 * Input stream archive reader.
 *
 * Reads an archive and its records from an input stream.
 */
public interface InputStreamArchiveReader {
    /**
     * Read a record from the archive.
     *
     * @return the next record, or null if the end of the archive is reached.
     * @throws AkaibuException if a format related exception occurs while
     * reading the record.
     * @throws IOException if an I/O error occurs while reading the record.
     */
    public byte[] read() throws AkaibuException, IOException;
}
