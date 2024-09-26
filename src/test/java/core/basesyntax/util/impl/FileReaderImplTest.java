package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderImpl fileReader;
    private static final String VALID_FILE_PATH = "src/test/resources/testFile.txt";
    private static final String INVALID_FILE_PATH = "invalid/path.txt";

    @BeforeEach
    public void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        Files.createDirectories(Paths.get("src/test/resources"));
        Files.write(Paths.get(VALID_FILE_PATH), List.of("test content"));
    }

    @Test
    public void readFile_validFilePath_ok() throws IOException {
        List<String> expected = List.of("test content");
        List<String> actual = fileReader.read(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileReader.read(INVALID_FILE_PATH));
    }
}
