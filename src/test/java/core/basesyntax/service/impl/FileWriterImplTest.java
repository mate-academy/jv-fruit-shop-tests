package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("test_data.txt"));
        Files.deleteIfExists(Path.of("output.txt"));
    }

    @Test
    void write_validData_writesToFile() throws IOException {
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("test content", "output.txt");

        String result = Files.readString(Path.of("output.txt"));
        assertEquals("test content", result);
    }
}
