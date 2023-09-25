package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterService writerService;
    private String outputPath;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        outputPath = "test-output.txt";
    }

    @Test
    void testWriteToFile() throws IOException {
        String data = "This is a test data";
        writerService.writeToFile(data, outputPath);

        Path path = Paths.get(outputPath);
        String content = new String(Files.readAllBytes(path));
        assertEquals(data, content);
    }

    @Test
    void testWriteToFileWithIOexception() {
        assertThrows(IOException.class, () -> {
            writerService.writeToFile("Data", "/nonexistentfolder/nonexistentfile.txt\"");
        });
    }
}
