package core.basesyntax;

import core.basesyntax.writer.FileWriterService;
import core.basesyntax.writer.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final Path FILE_PATH = Path.of("src/main/resources/test-report.csv");
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    void fileWriterService_createsFileWithCorrectContent_ok() throws IOException {
        String content = "banana,10\napple,20\n";

        fileWriterService.write(content, FILE_PATH.toString());
        Assertions.assertTrue(Files.exists(FILE_PATH));
        String actual = Files.readString(FILE_PATH);
        Assertions.assertEquals(content, actual);
    }
}
