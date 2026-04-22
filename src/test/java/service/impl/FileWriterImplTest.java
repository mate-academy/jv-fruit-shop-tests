package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    @TempDir
    private Path tempDir;

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validPath_ok() throws IOException {
        Path filePath = tempDir.resolve("output.csv");
        String content = "fruit,quantity\nbanana,20";

        fileWriter.write(content, filePath.toString());

        assertTrue(Files.exists(filePath));
        assertEquals(content, Files.readString(filePath));
    }

    @Test
    void write_invalidPath_notOk() {
        // Спроба записати в папку, якої не існує, або де немає прав
        assertThrows(RuntimeException.class, () ->
                fileWriter.write("data", "/invalid/path/file.csv"));
    }
}
