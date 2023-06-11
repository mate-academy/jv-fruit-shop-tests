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
    private static String testOutputFilePath;

    private static String reportHeader;
    private static WriterService writerService;
    private static String lineSeparator;

    @BeforeAll
    static void beforeAll() {
        testOutputFilePath = "src/test/resources/test-valid-report.csv";
        reportHeader = "fruit,quantity";
        lineSeparator = System.lineSeparator();
        writerService = new WriterServiceImpl();
    }

    @BeforeEach
    void setUp() {
        removeFile(testOutputFilePath);
    }

    @Test
    void writerService_validData_ok() {
        String report = reportHeader + lineSeparator + "banana,152" + lineSeparator + "apple,90";
        writerService.writeToFile(report, testOutputFilePath);
        String actual = readFromFile(testOutputFilePath);
        assertEquals(report, actual);
    }

    @Test
    void writerService_emptyString_ok() {
        String report = "";
        writerService.writeToFile(report, testOutputFilePath);
        String actual = readFromFile(testOutputFilePath);
        assertEquals(report, actual);
    }

    @Test
    void writerService_null_notOk() {
        assertThrows(NullPointerException.class, () -> writerService
                .writeToFile(null, testOutputFilePath));
    }

    private String readFromFile(String path) {
        try {
            return Files.readString(Path.of(testOutputFilePath));
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
