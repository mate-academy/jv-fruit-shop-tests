package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    private static final String TEST_FILE_NAME = "testFile.txt";
    private static final String NON_EXISTENT_FILE_NAME = "nonexistentFile.txt";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ShouldReturnFileContent_WhenFileExists(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve(TEST_FILE_NAME);
        Files.write(file, List.of("line1", "line2", "line3"));
        List<String> actualContent = fileReader.read(file.toString());
        List<String> expectedContent = List.of("line1", "line2", "line3");
        assertEquals(expectedContent, actualContent,
                "File content should match the expected lines.");
    }

    @Test
    void read_ShouldThrowRuntimeException_WhenFileDoesNotExist() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXISTENT_FILE_NAME),
                "Reading a non-existent file should throw RuntimeException.");
        assertEquals("Failed to read file: " + NON_EXISTENT_FILE_NAME,
                exception.getMessage(),
                "Exception message should match the expected error message.");
    }
}
