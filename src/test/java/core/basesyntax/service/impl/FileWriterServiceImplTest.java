package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output-test.csv";
    private static final String EMPTY_REPORT = "";
    private static final String INVALID_FILE_PATH = "";
    private FileWriterServiceImpl fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Checking for passing null as report value")
    @Test
    void writeReport_nullReport_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReport(null, OUTPUT_FILE_PATH));
    }

    @DisplayName("Checking for passing null as filePath value")
    @Test
    void writeReport_nullFilePath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReport(EMPTY_REPORT, null));
    }

    @DisplayName("Checking for passing null as report value and filePath")
    @Test
    void writeReport_nullReportAndNullFilePath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReport(null, null));
    }

    @DisplayName("Checking for passing invalid filePath")
    @Test
    void writeReport_invalidFilePath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReport(EMPTY_REPORT, INVALID_FILE_PATH));
    }

    @DisplayName("Checking for writing empty report to file")
    @Test
    void writeReport_writeEmptyReportToFile_ok() {
        fileWriterService.writeReport(EMPTY_REPORT, OUTPUT_FILE_PATH);
        String actual = readFile(OUTPUT_FILE_PATH);
        assertEquals(EMPTY_REPORT, actual);
    }

    @DisplayName("Checking for writing example report to file")
    @Test
    void writeReport_writeExampleReportToFile_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        fileWriterService.writeReport(expected, OUTPUT_FILE_PATH);
        String actual = readFile(OUTPUT_FILE_PATH);
        assertEquals(expected, actual);
    }

    private static String readFile(String filePath) {
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file by path: " + filePath, e);
        }
    }
}
