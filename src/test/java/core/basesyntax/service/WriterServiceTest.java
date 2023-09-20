package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceTest {
    public static final String PATH_TO_TEST_CSV_FILE = "src/test/resources/outputReport.txt";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_validData_ok() {
        File outputReport = new File(PATH_TO_TEST_CSV_FILE);
        String expected = String.format(
                "fruit,quantity%n"
                        + "banana,30%n"
                        + "apple,348"
        );

        writerService.writeToFile(PATH_TO_TEST_CSV_FILE, expected);
        Path path = Paths.get(PATH_TO_TEST_CSV_FILE);
        String actual = checkWriterResult(path);

        assertTrue(outputReport.exists());
        assertEquals(expected, actual);
    }

    @Test
    void writerService_emptyFile_ok() {
        File outputReport = new File(PATH_TO_TEST_CSV_FILE);
        String expected = "";

        writerService.writeToFile(PATH_TO_TEST_CSV_FILE, expected);
        Path path = Paths.get(PATH_TO_TEST_CSV_FILE);
        String actual = checkWriterResult(path);

        assertTrue(outputReport.exists());
        assertEquals(expected, actual);
    }

    private String checkWriterResult(Path path) {
        try {
            return Files.readAllLines(path).stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Try to read to a file was unsuccessful: " + e
            );
        }
    }
}
