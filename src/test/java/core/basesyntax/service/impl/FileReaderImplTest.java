package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void readFile_validFile_returnsContent() throws IOException {
        String content = "Line 1\nLine 2\nLine 3";
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Files.writeString(tempFile, content);

        List<String> lines = fileReader.readFile(tempFile.toString());

        assertEquals(3, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("Line 3", lines.get(2));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void readFile_nonExistentFile_throwsRuntimeException() {
        String nonExistentFilePath = "non_existent_file.txt";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile(nonExistentFilePath)
        );

        assertTrue(exception.getMessage().contains("Can't read file"));
    }

    @Test
    void readFile_emptyFile_returnsEmptyList() throws IOException {
        Path tempFile = Files.createTempFile("emptyFile", ".txt");

        List<String> lines = fileReader.readFile(tempFile.toString());

        assertTrue(lines.isEmpty());

        Files.deleteIfExists(tempFile);
    }
}
