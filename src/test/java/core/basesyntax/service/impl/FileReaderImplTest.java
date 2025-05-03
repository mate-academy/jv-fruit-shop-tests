package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CustomFileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderImplTest {
    private static final String TEST_FILE_NAME = "test_input.csv";
    private CustomFileReader fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ValidFile_ReturnsCorrectLines() throws IOException {
        Path testFilePath = tempDir.resolve(TEST_FILE_NAME);
        Files.write(testFilePath, List.of("apple,10", "banana,20"));

        File testFile = testFilePath.toFile();
        System.setProperty("FILE_PATH_INPUT_FILE", testFile.getAbsolutePath());

        List<String> result = fileReader.read();

        assertEquals(2, result.size());
        assertEquals("apple,10", result.get(0));
        assertEquals("banana,20", result.get(1));
    }

    @Test
    void read_FileCannotBeOpened_ThrowsRuntimeException() throws IOException {
        Path testFilePath = tempDir.resolve(TEST_FILE_NAME);
        Files.write(testFilePath, List.of("apple,10", "banana,20"));

        File testFile = testFilePath.toFile();
        System.setProperty("FILE_PATH_INPUT_FILE", testFile.getAbsolutePath());

        testFile.setReadable(false);

        assertThrows(RuntimeException.class, () -> fileReader.read());
    }
}
