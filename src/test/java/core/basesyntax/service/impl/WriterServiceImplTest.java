package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private ReaderService readerService;
    private TempFile tempFile;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
        tempFile = new TempFile();
    }

    @Test
    void readFromFile_OK() {
        File tmpFile = null;
        try {
            tmpFile = tempFile.createTempReportFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }

        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("fruit,quantity\n");
            writer.write("banana,152\n");
            writer.write("apple,90\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> lines = readerService.readFromFileReport(tmpFile.getPath());
        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
        Assertions.assertEquals("banana,152", lines.get(1));
        Assertions.assertEquals("apple,90", lines.get(2));
        tmpFile.delete();
    }

    @Test
    void isFileEmpty() {
        File tmpFile = null;
        try {
            tmpFile = tempFile.createTempReportFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create report file", e);
        }
        try {
            assertTrue(tmpFile.length() == 0, "File should be empty");
        } finally {
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }

    @Test
    void readFromInvalidFile_NotOk() {
        File tmpFile = null;
        try {
            tmpFile = tempFile.createTempReportFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create report file", e);
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
                () -> readerService.readFromFileReport(path));
        tmpFile.delete();
    }
}
