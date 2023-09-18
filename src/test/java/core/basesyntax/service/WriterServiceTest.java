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
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_validData_ok() {
        String pathToTestCsvFile = "src/test/resources/outputReport.txt";
        File outputReport = new File(pathToTestCsvFile);
        String expected = String.format(
                "fruit,quantity%n"
                        + "banana,30%n"
                        + "apple,348"
        );

        writerService.writeToFile(pathToTestCsvFile, expected);
        Path path = Paths.get(pathToTestCsvFile);
        String actual;

        try {
            actual = Files.readAllLines(path).stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Try to write to a file was unsuccessful: " + e
            );
        }

        assertTrue(outputReport.exists());
        assertEquals(expected, actual);
    }
}
