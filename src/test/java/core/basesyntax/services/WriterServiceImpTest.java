package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.WriteDataToFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterServiceImpTest {
    private static final String expectedData = "Hello, world!";
    private static final String emptyData = "";
    private static final String subfolder = "subfolder";
    private static final String test = "test";
    private static final String format = ".txt";

    private WriterServiceImp writerServiceImp;

    @BeforeEach
    void setUp() {
        writerServiceImp = new WriterServiceImp();
    }

    @Test
    void write_NullFilePath_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> writerServiceImp.write(null, "data"));
    }

    @Test
    void write_NullData_ThrowsException(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.createTempFile(tempDir, test, format);
        assertThrows(IllegalArgumentException.class, () ->
                writerServiceImp.write(filePath.toString(), null));
    }

    @Test
    void write_EmptyData_ThrowsException(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.createTempFile(tempDir, test, format);
        assertThrows(IllegalArgumentException.class, () ->
                writerServiceImp.write(filePath.toString(), emptyData));
    }

    @Test
    void write_NullFilePathAndData_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> writerServiceImp.write(null, null));
    }

    @Test
    void write_ValidFilePathAndData_WritesCorrectData(@TempDir Path filePath) throws IOException {
        Path tempFile = Files.createTempFile(filePath, test, format);
        writerServiceImp.write(tempFile.toString(), expectedData);
        String actualData = Files.readString(tempFile);
        assertEquals(expectedData, actualData);
    }

    @Test
    void write_WrongFilePathToDirectory_ThrowsException(@TempDir Path tempDir) throws IOException {
        Path directory = Files.createDirectory(tempDir.resolve(subfolder));
        assertThrows(WriteDataToFileException.class, () ->
                writerServiceImp.write(directory.toString(), expectedData)
        );
    }

    @Test
    void write_ReadOnlyFile_ThrowsException(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, test, format);
        tempFile.toFile().setWritable(false);
        assertThrows(WriteDataToFileException.class, () ->
                writerServiceImp.write(tempFile.toString(), expectedData)
        );
    }
}
