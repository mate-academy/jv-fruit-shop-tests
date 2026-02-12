package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.InvalidDataException;
import core.basesyntax.service.DataWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class CsvDataWriterImplTest {
    private static final String PATH = "src/main/resources";
    private static final String EMPTY_STRING = "";
    private static final String FILENAME = "result_file.csv";
    private static final String NULL_VALUE = null;
    private static final String TITLE = "fruit,quantity";
    private static final String REPORT = TITLE + System.lineSeparator() + "pear,50";
    private DataWriter dataWriter;

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(PATH + File.separator + FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear result file after test " + FILENAME);
        }
    }

    @Test
    void writeToFile_EmptyStringArgument_NotOk() {
        dataWriter = new CsvDataWriterImpl(REPORT);
        assertThrows(RuntimeException.class, () -> dataWriter.writeToFile(EMPTY_STRING));
    }

    @Test
    void writeToFile_NullValueReport_NotOk() {
        dataWriter = new CsvDataWriterImpl(NULL_VALUE);
        assertThrows(InvalidDataException.class, () -> dataWriter.writeToFile(FILENAME));
    }

    @Test
    void writeToFile_NullValueArgument_NotOk() {
        dataWriter = new CsvDataWriterImpl(REPORT);
        assertThrows(InvalidDataException.class, () -> dataWriter.writeToFile(NULL_VALUE));
    }

    @Test
    void writeToFile_EmptyReport_Ok() {
        dataWriter = new CsvDataWriterImpl(EMPTY_STRING);
        dataWriter.writeToFile(FILENAME);
        String actual = readFromFile(FILENAME);
        assertEquals(EMPTY_STRING, actual);
    }

    @Test
    void writeToFile_ValidReportAndFileName_Ok() {
        dataWriter = new CsvDataWriterImpl(REPORT);
        dataWriter.writeToFile(FILENAME);
        String actual = readFromFile(FILENAME);
        assertEquals(REPORT, actual);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(PATH + File.separator + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName);
        }
    }
}
