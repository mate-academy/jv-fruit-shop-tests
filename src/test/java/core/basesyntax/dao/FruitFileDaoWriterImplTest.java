package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FruitFileDaoWriterImplTest {

    private final FruitFileDaoWriterImpl writer = new FruitFileDaoWriterImpl();

    @Test
    void write_shouldWriteContentToFile() throws IOException {
        String content = "Test content";
        Path tempFile = Files.createTempFile("testFile", ".txt");
        writer.write(content, tempFile.toString());
        String fileContent = Files.readString(tempFile);
        assertEquals(content, fileContent);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_shouldThrowExceptionForEmptyFileName() {
        String content = "Test content";
        assertThrows(RuntimeException.class, () -> writer.write(content, ""));
    }

    @Test
    void write_shouldHandleIoException() {
        String content = "Test content";
        String invalidFilePath = "/invalidPath/testFile.txt";
        assertThrows(RuntimeException.class, () -> writer.write(content, invalidFilePath));
    }

    @Test
    void write_shouldOverwriteExistingFile() throws IOException {
        String initialContent = "Initial content";
        String newContent = "New content";
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Files.writeString(tempFile, initialContent);
        writer.write(newContent, tempFile.toString());
        String fileContent = Files.readString(tempFile);
        assertEquals(newContent, fileContent);
        Files.deleteIfExists(tempFile);
    }
}
