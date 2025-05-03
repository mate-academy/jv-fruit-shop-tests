package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit, quantity"
            + System.lineSeparator() + "banana, 10";
    private static final String PATH_TO_FILE = "src/test/resources/reportFile.csv";
    private static final String INVALID_PATH = "";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(PATH_TO_FILE));
    }

    @Test
    void writeToFile_walidReport_ok() throws IOException {
        writerService.writeToFile(PATH_TO_FILE, EXPECTED_REPORT);
        assertTrue(Files.exists(Path.of(PATH_TO_FILE)));
        assertEquals(EXPECTED_REPORT, Files.readString(Path.of(PATH_TO_FILE)));
    }

    @Test
    void writeToFile_pathIsEmpty_notOk() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(INVALID_PATH, EXPECTED_REPORT));
        assertEquals("Unable to write report to file at path:", expectedMessage.getMessage());
    }
}
