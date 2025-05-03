package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static final Path FILE_PATH = Path.of("src/test/resources/outputFile.txt");
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testWriteToFile() throws IOException {
        String content = "This is a test content.";
        fileWriterService.writeToFile(content, FILE_PATH.toString());

        String fileContent = Files.readString(FILE_PATH);
        assertEquals(content, fileContent);
    }

    @Test
    public void testWriteToFile_EmptyContent() throws IOException {
        String content = "";
        fileWriterService.writeToFile(content, FILE_PATH.toString());

        String fileContent = Files.readString(FILE_PATH);
        assertEquals(content, fileContent);
    }

    @Test
    public void testWriteToFile_Ioexception() {
        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeToFile("Test", "src/test/resources");
        });
    }
}
