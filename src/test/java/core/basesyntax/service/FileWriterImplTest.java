package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriter writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void write_valid_ok() throws Exception {
        Path temp = Files.createTempFile("out", ".txt");
        writer.write("hello", temp.toString());

        String content = Files.readString(temp);
        assertEquals("hello", content);
    }

    @Test
    void write_invalidPath_throws() {
        assertThrows(RuntimeException.class,
                () -> writer.write("x", "/bad/path/out.txt"));
    }
}
