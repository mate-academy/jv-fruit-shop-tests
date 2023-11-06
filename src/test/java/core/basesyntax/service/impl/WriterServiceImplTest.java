package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String VALID_FILE_PATH = "test_valid_report.csv";
    private static final String REPORT_CONTENT = "fruit,quantity\nbanana,152\napple,90\n";
    private static final String ERROR_MESSAGE = "file Path can't be null";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_validPath_ok() {
        writerService.writeToFile(VALID_FILE_PATH, REPORT_CONTENT);
        List<String> actualLines = readLinesFromFile(VALID_FILE_PATH);
        List<String> expectedLines = List.of(REPORT_CONTENT.split("\n"));
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    void writeToFile_null_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, ""),
                "method should throw an exception when the path is null");
        assertEquals(ERROR_MESSAGE, runtimeException.getMessage());
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Paths.get(VALID_FILE_PATH));
        } catch (IOException e) {
            fail("Failed to delete the test file report");
        }
    }

    private List<String> readLinesFromFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            fail("Exception shouldn't be thrown in this test");
            return List.of();
        }
    }
}
