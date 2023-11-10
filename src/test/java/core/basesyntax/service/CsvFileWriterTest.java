package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String TEST_FILE_NAME = "report.csv";
    private static final String EXCEPTION_MESSAGE = "Can't write to file";

    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
    }

    @AfterEach
    void afterEach() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file", e);
        }
    }

    @Test
    void write_nullInputData_NotOk() {
        String content = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(content, TEST_FILE_NAME),
                EXCEPTION_MESSAGE);
    }

    @Test
    void write_nullFileName_NotOk() {
        String content = "valid";
        String fileName = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(content, fileName),
                EXCEPTION_MESSAGE);
    }

    @Test
    void write_validData_Ok() {
        String content = "valid";
        assertTrue(fileWriter.write(content, TEST_FILE_NAME));
    }
}
