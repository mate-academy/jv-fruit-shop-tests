package service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportWriter;

class ReportWriterTest {
    private static final String DESTINATION_FILE = "report.csv";
    private static ReportWriter reportWriter;
    private String dataToWrite;

    @BeforeAll
    static void beforeAll() {
        reportWriter = new ReportWriterImpl();
    }

    @BeforeEach
    void setUp() {
        dataToWrite = "fruit,quantity".concat(System.lineSeparator())
                .concat("apple,18").concat(System.lineSeparator())
                .concat("grape,47").concat(System.lineSeparator())
                .concat("pear,26").concat(System.lineSeparator());
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(DESTINATION_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Cannot correctly clear result file after test ", e);
        }
    }

    @Test
    void writeReport_ok() {
        reportWriter.writeReport(dataToWrite, DESTINATION_FILE);
        String actualReport = readFromFile(DESTINATION_FILE);
        String expectedReport = dataToWrite;
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void writeReport_destinationFileDoesExist_ok() {
        assertDoesNotThrow(() -> reportWriter.writeReport(
                dataToWrite, DESTINATION_FILE));
        assertTrue(Files.exists(Path.of(DESTINATION_FILE)));
    }

    @Test
    void writeReport_overwritesExistingContent() throws IOException {
        Files.writeString(Path.of(DESTINATION_FILE), dataToWrite);
        reportWriter.writeReport(dataToWrite, DESTINATION_FILE);
        String actualReport = readFromFile(DESTINATION_FILE);
        assertEquals(dataToWrite, actualReport);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
