package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String TEST_OUTPUT = "src/main/resources/test_report.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_validPath_ok() throws IOException {
        String testContent = "fruit,quantity" + System.lineSeparator() + "banana,10";
        fileWriter.write(testContent, TEST_OUTPUT);

        Path path = Path.of(TEST_OUTPUT);
        assertTrue(Files.exists(path), "File should be created.");

        String actualContent = Files.readString(path);
        assertEquals(testContent, actualContent,
                "Content in file should match what was written.");

        Files.deleteIfExists(path);
    }
}
