package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    @TempDir
    private Path tempDir;
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validPath_ok() throws IOException {
        Path file = tempDir.resolve("output.csv");
        String content = "fruit,quantity" + System.lineSeparator() + "banana,20";

        fileWriter.write(content, file.toString());

        assertEquals(content, Files.readString(file));
    }

    @Test
    void write_nonExistingDirectory_notOk() {
        String path = tempDir.resolve("missing").resolve("output.csv").toString();

        assertThrows(RuntimeException.class, () -> fileWriter.write("content", path));
    }
}
