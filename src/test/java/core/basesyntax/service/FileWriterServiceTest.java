package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {
    private static final Path FILE_PATH = Path.of("src/test/resources/finalReport.csv");
    private static final String STRING_PATH = "src/test/resources/finalReport.csv";
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    void writeToFile_ValidData_Ok() {
        String report = "fruit,quantity";
        fileWriterService.write(report, STRING_PATH);
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String line = reader.readLine();
            assertEquals(report, line);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }

    @Test
    void writeToFile_nullRecord_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriterService.write(null, STRING_PATH));
    }

    @Test
    void testWriteToFile_NullPath_ThrowsException() {
        assertThrows(NullPointerException.class,
                () -> fileWriterService.write("Test data", null));
    }
}
