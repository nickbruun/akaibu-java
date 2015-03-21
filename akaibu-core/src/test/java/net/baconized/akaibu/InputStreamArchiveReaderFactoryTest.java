package net.baconized.akaibu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class InputStreamArchiveReaderFactoryTest {
    private final InputStreamArchiveReader createFromBytes(byte[] data)
        throws InvalidCompressionException,
               InvalidArchiveException,
               UnsupportedVersionException,
               IOException {
        return InputStreamArchiveReaderFactory.create(
            new ByteArrayInputStream(data)
        );
    }

    @Test
    public void testCreate() throws Exception {
        // Initializing a new reader with less than header data throws
        // IOException.
        for (int l = 0; l < 7; ++l) {
            try {
                createFromBytes(
                    Arrays.copyOfRange(ArchiveFixtures.v1UncompressedHeader,
                                       0,
                                       l)
                );
                Assert.fail("Expected IOException to have been thrown");
            }
            catch (IOException e) {
                // Expected exception.
            }
        }

        // Initializing a new reader with unsupported version throws
        // UnsupportedVersionException.
        try {
            createFromBytes(ArchiveFixtures.v2Header);
            Assert.fail("Expected UnsupportedVersionException to have been thrown");
        }
        catch (UnsupportedVersionException e) {
            // Expected exception.
        }

        // Creating a reader with unsupported compression throws
        // InvalidCompressionException.
        try {
            createFromBytes(ArchiveFixtures.v1InvalidCompressionHeader);
            Assert.fail("Expected InvalidCompressionException to have been thrown");
        }
        catch (InvalidCompressionException e) {
            // Expected exception.
        }

        // Creating a reader for an uncompressed, empty archive succeeds.
        InputStreamArchiveReader reader =
            createFromBytes(ArchiveFixtures.v1UncompressedHeader);
        Assert.assertNotNull(reader);
    }

    private void testCreateWithSample(String resourcePath,
                                      Class<?> cls) throws Exception {
        // Create the reader.
        InputStreamArchiveReader reader =
            InputStreamArchiveReaderFactory.create(
                getClass()
                .getResourceAsStream(resourcePath)
            );

        if (!reader.getClass().isAssignableFrom(cls))
            Assert.fail(String.format("Expected reader to be an instance of %s, but it is an instance of %s", cls.getName(), reader.getClass().getName()));

        // Test the records.
        int read = 0;

        while (true) {
            byte[] data = reader.read();
            if (data == null)
                break;

            ++read;

            Assert.assertEquals(data.length, read * 100);
        }

        Assert.assertEquals(read, 10);
    }

    @Test
    public void testCreateWithSamples() throws Exception {
        testCreateWithSample("/fixtures/uncompressed.v1.akaibu",
                             InputStreamUncompressedArchiveReader.class);
        testCreateWithSample("/fixtures/snappy.v1.akaibu",
                             InputStreamSnappyCompressedArchiveReader.class);
        testCreateWithSample("/fixtures/zlib.v1.akaibu",
                             InputStreamZlibCompressedArchiveReader.class);
    }
}
