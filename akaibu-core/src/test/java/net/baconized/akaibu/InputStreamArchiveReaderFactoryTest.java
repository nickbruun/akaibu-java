package net.baconized.akaibu;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class InputStreamArchiveReaderFactoryTest {
    private final InputStreamArchiveReader createFromFixture(byte[] data)
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
                createFromFixture(
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
            createFromFixture(ArchiveFixtures.v2Header);
            Assert.fail("Expected UnsupportedVersionException to have been thrown");
        }
        catch (UnsupportedVersionException e) {
            // Expected exception.
        }

        // Creating a reader with unsupported compression throws
        // InvalidCompressionException.
        try {
            createFromFixture(ArchiveFixtures.v1InvalidCompressionHeader);
            Assert.fail("Expected InvalidCompressionException to have been thrown");
        }
        catch (InvalidCompressionException e) {
            // Expected exception.
        }

        // Creating a reader for an uncompressed, empty archive succeeds.
        InputStreamArchiveReader reader =
            createFromFixture(ArchiveFixtures.v1UncompressedHeader);
        Assert.assertNotNull(reader);
    }
}
