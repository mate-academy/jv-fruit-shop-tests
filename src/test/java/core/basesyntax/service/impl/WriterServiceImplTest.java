package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/ReportTest.csv";
    private static final String REPORT = "banana, 100";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_validPath_ok() {
        writerService.writeToFile(VALID_PATH, REPORT);
        String expected = REPORT;
        String actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_PATH)).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void writeToFile_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, REPORT));
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("src/test/nonExistentPackage/file.csv", REPORT));
    }

    @Test
    void writeToFile_nullReport_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(VALID_PATH, null));
    }
}
