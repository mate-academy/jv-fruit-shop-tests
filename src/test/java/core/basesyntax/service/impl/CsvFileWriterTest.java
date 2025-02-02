package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    @Test
    void writeReport_validDataAndPath_shouldWriteToFile() throws IOException {
        CsvFileWriter csvFileWriter = new CsvFileWriter();
        String data = "Test data,123,456";
        Path tempFile = Files.createTempFile("test-file", ".csv");
        String path = tempFile.toString();
        csvFileWriter.writeReport(data, path);
        String writtenData = Files.readString(tempFile);
        assertEquals(data, writtenData);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void writeReport_invalidPath_shouldThrowRuntimeException() {
        CsvFileWriter csvFileWriter = new CsvFileWriter();
        String data = "Invalid data test";
        String invalidPath = "/invalid/path/test.csv";
        assertThrows(RuntimeException.class, () ->
                csvFileWriter.writeReport(data, invalidPath)
        );
    }

    @Test
    void writeReport_emptyData_validPath_shouldCreateEmptyFile() throws IOException {
        CsvFileWriter csvFileWriter = new CsvFileWriter();
        String data = "";
        Path tempFile = Files.createTempFile("empty-data", ".csv");
        String path = tempFile.toString();
        csvFileWriter.writeReport(data, path);
        String writtenData = Files.readString(tempFile);
        assertTrue(writtenData.isEmpty());
        Files.deleteIfExists(tempFile);
    }
}
