package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderImpl reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("a,apple,10", "b,banana,20"));
        List<String> actual = reader.read(tempFile.toString());
        assertEquals(2, actual.size());
        assertEquals("a,apple,10", actual.get(0));
        assertEquals("b,banana,20", actual.get(1));
    }

    @Test
    void read_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read("non-existing-file-123.csv"));
    }

    @Test
    void read_emptyFile_notOk() throws IOException {
        Path tempFile = Files.createTempFile("empty", ".csv");
        assertThrows(RuntimeException.class,
                () -> reader.read(tempFile.toString()));
    }
}
