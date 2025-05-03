package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntex.io.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {

    @TempDir
    private Path tempDir;

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validContent_ok() throws IOException {
        Path file = tempDir.resolve("testFile.txt");
        String content = "Test, Content";
        fileWriter.write(content, file.toString());
        List<String> result = Files.readAllLines(file);
        assertEquals(List.of("Test, Content"), result);
    }

    @Test
    void write_emptyContent_ok() throws IOException {
        Path file = tempDir.resolve("emptyFile.txt");
        String content = "";
        fileWriter.write(content, file.toString());
        List<String> result = Files.readAllLines(file);
        assertTrue(result.isEmpty());
    }

    @Test
    void write_nonWritableLocation_notOk() {
        String invalidFilePath = "/invalidPath/testFile.txt";
        String content = "Hello, World!";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileWriter.write(content, invalidFilePath);
        });
        assertTrue(exception.getMessage().contains("Error writing file"));
    }
}
