package core.basesyntax.filereader;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test-report.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/nonexistent.csv";
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();

        String testContent = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "s,apple,100\n";
        Files.write(Path.of(TEST_FILE_PATH), testContent.getBytes());

        Files.write(Path.of(EMPTY_FILE_PATH), new byte[0]);
    }

    @Test
    void read_ValidFile_ReturnsCorrectList() {
        List<String> result = fileReader.read(TEST_FILE_PATH);

        assertNotNull(result, "The list of terms must not be null");
        assertEquals(3, result.size(), "The file must contain 3 lines");
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("b,banana,20", result.get(1));
        assertEquals("s,apple,100", result.get(2));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList() {
        List<String> result = fileReader.read(EMPTY_FILE_PATH);

        assertNotNull(result, "Empty file should return an empty list, not null");
        assertTrue("List should be empty for an empty file", result.isEmpty());
    }

    @Test
    void read_NonExistentFile_ThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                fileReader.read(NON_EXISTENT_FILE_PATH)
        );

        assertTrue(exception.getMessage().contains("Cant read file by path: "));
    }
}
