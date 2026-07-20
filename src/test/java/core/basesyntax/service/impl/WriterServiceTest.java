package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final Map<String, Integer> EMPTY_REPORT = new LinkedHashMap<>();
    private static final String EXPECTED_CONTENT =
            """
            fruit,quantity
            banana,1
            apple,2
            """;
    private static final Map<String, Integer> EXPECTED_REPORT = new LinkedHashMap<>();
    private static final String FILE_STRING_PATH = "src/test/resources/report.csv";
    private static final String DIRECTORY_PATH = "src/test/resources";
    private static final Path FILE_PATH = Path.of(FILE_STRING_PATH);
    private static final WriterService writer = new WriterServiceImpl();

    @BeforeEach
    void setUp() {
        EXPECTED_REPORT.put("banana", 1);
        EXPECTED_REPORT.put("apple", 2);
    }

    @AfterEach
    void deleteTestData() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    void dataWriterTest_checkCorrectFile_ok() {
        writer.writeToFile(EXPECTED_REPORT, FILE_STRING_PATH);
        assertTrue(Files.exists(FILE_PATH));
    }

    @Test
    void dataWriterTest_checkFileContent_ok() throws IOException {
        writer.writeToFile(EXPECTED_REPORT, FILE_STRING_PATH);
        assertEquals(EXPECTED_CONTENT, Files.readString(FILE_PATH));
    }

    @Test
    void dataWriterTest_notCorrectPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writer.writeToFile(EMPTY_REPORT, DIRECTORY_PATH));
    }
}
