package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid-output.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-output.csv";
    private static final String INVALID_FILE_PATH = "qwe/qwe/valid-output.csv";
    private static final String DATA = "Test data,123,456";
    private static final String EMPTY_DATA = "";

    @Test
    void writeReport_validDataAndPath_ok() throws IOException {
        FileWriter csvFileWriter = new CsvFileWriter();
        Path path = Path.of(VALID_FILE_PATH);
        csvFileWriter.writeReport(DATA, VALID_FILE_PATH);
        String actual = Files.readString(path);
        assertEquals(DATA, actual);
        Files.deleteIfExists(path);
    }

    @Test
    void writeReport_invalidPath_notOk() {
        FileWriter csvFileWriter = new CsvFileWriter();
        assertThrows(RuntimeException.class, () ->
                csvFileWriter.writeReport(DATA, INVALID_FILE_PATH)
        );
    }

    @Test
    void writeReport_emptyData_validPath_ok() throws IOException {
        FileWriter csvFileWriter = new CsvFileWriter();
        Path path = Path.of(EMPTY_FILE_PATH);
        csvFileWriter.writeReport(EMPTY_DATA, EMPTY_FILE_PATH);
        String actual = Files.readString(path);
        assertTrue(actual.isEmpty());
        Files.deleteIfExists(path);
    }
}
