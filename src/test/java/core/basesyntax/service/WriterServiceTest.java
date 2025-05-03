package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String INVALID_FILE_PATH = "src/test/resources/..";
    private static final String VALID_FILE_PATH = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeTo_invalidFilePath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "orange,40";
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(INVALID_FILE_PATH, report)
        );
    }

    @Test
    void writeTo_validFilePath_notOk() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "orange,40";
        assertDoesNotThrow(() -> writerService.writeToFile(VALID_FILE_PATH, report));
        assertEquals(Files.readString(Path.of(VALID_FILE_PATH)), report);
    }
}
