package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private FileWriterImpl fileWriterImpl;
    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriterImpl = new FileWriterImpl();
    }

    @Test
    void write_validFileAndData_Ok() throws IOException {
        Path file = tempDir.resolve("test.txt");
        String expected = "This is fruit storage!";
        fileWriterImpl.write(file.toString(), expected);
        String actual = Files.readString(file);
        assertEquals(expected, actual);
    }

    @Test
    void write_nullFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriterImpl.write(null, "Hello World!"));
        assertEquals("Data and file name cannot be null", exception.getMessage());
    }

    @Test
    void write_nullData_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriterImpl.write("data", null));
        assertEquals("Data and file name cannot be null", exception.getMessage());
    }

    @Test
    void write_invalidFilePath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriterImpl.write("/qwer/qewer", "Hello!"));
        assertTrue(exception.getMessage().contains("Failed to write data to file"));
    }
}
