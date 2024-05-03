package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {
    private static final String TEST_CONTENT = "This is a test file.";
    private static final String WRONG_FILE_PATH = "non-existing-file.txt";
    private FileReaderService reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderServiceImpl();
    }

    @Test
    public void read_NotThrows_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, TEST_CONTENT.getBytes());

        String expectedContent = TEST_CONTENT;
        String actualContent = reader.read(tempFile.toString());
        assertEquals(expectedContent, actualContent);

        Files.delete(tempFile);
    }

    @Test
    public void readNonExistingFile_Test() {
        assertThrows(IOException.class, () ->
                reader.read(WRONG_FILE_PATH));
    }
}
