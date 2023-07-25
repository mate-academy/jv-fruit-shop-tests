package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderServiceImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplImplTest {
    private ReaderServiceImpl readerServiceImpl;
    private TempFile tempFile;

    @BeforeEach
    void setUp() {
        readerServiceImpl = new ReaderServiceImplImpl();
        tempFile = new TempFile();
    }

    @Test
    void readFromInvalidFile_NotOk() {
        File tmpFile = null;
        try {
            tmpFile = tempFile.createTempInputFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create input file", e);
        }

        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("test message1\n");
            writer.write("aA1!@:',&*\n");
            writer.write("test message2\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String path = tmpFile.getPath();
        assertThrows(RuntimeException.class,
                () -> readerServiceImpl.readFromFileReport(path));
        tmpFile.delete();
    }

    @Test
    void readFromNonExistentFile_NotOk() {
        String nonExistentFilePath = "non-existent-file.csv";
        assertThrows(RuntimeException.class,
                () -> readerServiceImpl.readFromFileReport(nonExistentFilePath));
    }

    @Test
    void isFileEmpty() {
        File tmpFile = null;
        try {
            tmpFile = tempFile.createTempInputFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create input file", e);
        }
        try {
            assertTrue(tmpFile.length() == 0, "File should be empty");
        } finally {
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }
}
