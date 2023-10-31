package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static FileWriterService fileWriterService;
    private static final String VALID_FILE_PATH = "src/test/java/resources/report.csv";
    private static final String INVALID_FILE_PATH = "src/test/java/resources/..";

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeTo_validFilePath_ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertDoesNotThrow(() -> fileWriterService.writeToFile(VALID_FILE_PATH, report));
        assertEquals(Files.readString(Path.of(VALID_FILE_PATH)), report);

    }

    @Test
    void writeTo_invalidFilePath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(INVALID_FILE_PATH, report)
        );
    }

    @Test
    void writeTo_nullReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(INVALID_FILE_PATH, null)
        );
    }
}
