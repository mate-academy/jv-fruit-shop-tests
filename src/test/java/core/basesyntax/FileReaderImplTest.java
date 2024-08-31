package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntex.io.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {

    @TempDir
    private Path tempDir;

    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_existingFileWithContent_ok() throws IOException {
        Path file = tempDir.resolve("testFile.txt");
        Files.write(file, List.of("line1", "line2"));
        List<String> result = fileReader.read(file.toString());
        assertEquals(List.of("line1", "line2"), result);
    }

    @Test
    void read_emptyFile_ok() throws IOException {
        Path file = tempDir.resolve("emptyFile.txt");
        Files.write(file, List.of());
        List<String> result = fileReader.read(file.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_nonExistentFile_notOk() {
        String nonExistentFilePath = "nonExistentFile.txt";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(nonExistentFilePath);
        });
        assertTrue(exception.getMessage().contains("Error reading file"));
    }
}
