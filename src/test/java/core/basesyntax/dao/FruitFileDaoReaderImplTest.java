package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitFileDaoReaderImplTest {

    private final FruitFileDaoReaderImpl reader = new FruitFileDaoReaderImpl();

    @Test
    void read_shouldReturnFileContentAsList() throws IOException {
        String content = "Line 1\nLine 2\nLine 3";
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Files.writeString(tempFile, content);
        List<String> fileLines = reader.read(tempFile.toString());
        assertEquals(List.of("Line 1", "Line 2", "Line 3"), fileLines);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_shouldReturnEmptyListForEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("emptyFile", ".txt");
        List<String> fileLines = reader.read(tempFile.toString());
        assertTrue(fileLines.isEmpty());
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_shouldThrowExceptionForNonExistentFile() {
        String nonExistentFilePath = "nonexistent.txt";
        assertThrows(RuntimeException.class, () -> reader.read(nonExistentFilePath));
    }

    @Test
    void read_shouldHandleLargeFiles() throws IOException {
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeContent.append("Line ").append(i).append("\n");
        }
        Path tempFile = Files.createTempFile("largeFile", ".txt");
        Files.writeString(tempFile, largeContent.toString());
        List<String> fileLines = reader.read(tempFile.toString());
        assertEquals(10000, fileLines.size());
        assertEquals("Line 0", fileLines.get(0));
        assertEquals("Line 9999", fileLines.get(9999));
        Files.deleteIfExists(tempFile);
    }
}
