package core.basesyntax.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

    private static final String VALID_FILE_PATH = "test_valid_file.csv";
    private static final String UNREADABLE_FILE = "unreadable_file.csv";
    private static final String EMPTY_FILE = "empty_test_file.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReader();
        Path validFilePath = Path.of(VALID_FILE_PATH);
        Files.write(validFilePath, List.of("line1", "line2", "line3"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(UNREADABLE_FILE));
    }

    @Test
    void read_validFile_returnsContent() {
        List<String> result = fileReader.read(VALID_FILE_PATH);
        assertEquals(3, result.size());
        assertEquals("line1", result.get(0));
        assertEquals("line2", result.get(1));
        assertEquals("line3", result.get(2));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        String emptyFilePath = EMPTY_FILE;
        Files.createFile(Path.of(emptyFilePath));
        List<String> result = fileReader.read(emptyFilePath);
        assertEquals(0, result.size());
    }
}
