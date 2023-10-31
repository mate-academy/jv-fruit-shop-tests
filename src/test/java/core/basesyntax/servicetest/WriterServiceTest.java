package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceTest {
    private static final String INVALID_FILE_PATH = "src/test/resources/..";
    private static final String VALID_FILE_PATH = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_validFilePath_Ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        assertDoesNotThrow(() -> writerService.writeToFile(report, VALID_FILE_PATH));
        assertEquals(report, Files.readString(Path.of(VALID_FILE_PATH)));
    }

    @Test
    void writeToFile_invalidFilePath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(report, INVALID_FILE_PATH)
        );
    }
}
