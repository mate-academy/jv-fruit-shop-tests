package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {

    private static final String FILE_NAME = "test_write_file.txt";
    @TempDir
    private File tempDir;
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validDataAndPath_ok() throws IOException {
        Path path = tempDir.toPath().resolve(FILE_NAME);
        String testData = "Hello, world!";

        fileWriter.write(testData, path.toString());

        assertTrue(Files.exists(path));
        byte[] writtenBytes = Files.readAllBytes(path);
        assertArrayEquals(testData.getBytes(), writtenBytes);
    }

    @Test
    void write_emptyData_ok() throws IOException {
        Path path = tempDir.toPath().resolve(FILE_NAME);
        String emptyData = "";

        fileWriter.write(emptyData, path.toString());

        assertTrue(Files.exists(path));
        byte[] writtenBytes = Files.readAllBytes(path);
        assertArrayEquals(emptyData.getBytes(), writtenBytes);
    }

    @Test
    void write_invalidPath_throwRuntimeException() {
        String invalidPath = "/invalid/non_existent/path/file.txt";
        String data = "some data";

        assertThrows(RuntimeException.class, () -> fileWriter.write(data, invalidPath));
    }
}
