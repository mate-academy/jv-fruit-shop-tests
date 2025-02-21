package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.services.FileWriterService;
import core.basesyntax.services.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {

    @Test
    void writeToValidFile() throws IOException {
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.write("src/test/resources/output.csv", "Test content");
        assertTrue(Files.exists(Paths.get("src/test/resources/output.csv")));
        String content = new String(Files.readAllBytes(Paths.get("src/test/resources/output.csv")));
        assertEquals("Test content", content);
    }

    @Test
    void writeNullData() throws IOException {
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.write("src/test/resources/output.csv", null);
        assertTrue(Files.exists(Paths.get("src/test/resources/output.csv")));
        String content = new String(Files.readAllBytes(Paths.get("src/test/resources/output.csv")));
        assertEquals("", content);
    }
}
