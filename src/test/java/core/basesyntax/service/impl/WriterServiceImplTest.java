package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String TEST_OUTPUT_FILE_PATH = "src/test/resources/test-valid-report.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String reportHeader;
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        reportHeader = "fruit,quantity";
        writerService = new WriterServiceImpl();
    }

    @BeforeEach
    void setUp() {
        removeFile(TEST_OUTPUT_FILE_PATH);
    }

    @Test
    void writerService_validData_ok() {
        String report = reportHeader + LINE_SEPARATOR + "banana,152" + LINE_SEPARATOR + "apple,90";
        writerService.writeToFile(report, TEST_OUTPUT_FILE_PATH);
        String actual = readFromFile(TEST_OUTPUT_FILE_PATH);
        assertEquals(report, actual);
    }

    @Test
    void writerService_emptyString_ok() {
        String report = "";
        writerService.writeToFile(report, TEST_OUTPUT_FILE_PATH);
        String actual = readFromFile(TEST_OUTPUT_FILE_PATH);
        assertEquals(report, actual);
    }

    @Test
    void writerService_null_notOk() {
        assertThrows(NullPointerException.class, () -> writerService
                .writeToFile(null, TEST_OUTPUT_FILE_PATH));
    }

    private String readFromFile(String path) {
        try {
            return Files.readString(Path.of(TEST_OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeFile(String path) {
        try {
            Files.delete(Path.of(path));
        } catch (NoSuchFileException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file: " + path, e);
        }
    }
}
