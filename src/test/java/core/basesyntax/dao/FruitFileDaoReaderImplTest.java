package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitFileDaoReaderImplTest {

    private static FruitFileDaoReaderImpl reader;

    private static final String VALID_FILE_CONTENT = "Linia 1\nLinia 2\nLinia 3";
    private static final List<String> EXPECTED_VALID_FILE_LINES = List.of("Linia 1",
            "Linia 2", "Linia 3");
    private static final String NON_EXISTENT_FILE_PATH = "nonexistent.txt";
    private static final String LARGE_FILE_PREFIX = "Linia ";
    private static final int LARGE_FILE_LINES_COUNT = 10000;

    private static final String TEST_FILE_NAME = "testFile";
    private static final String EMPTY_FILE_NAME = "emptyFile";
    private static final String LARGE_FILE_NAME = "largeFile";
    private static final String FILE_EXTENSION = ".txt";

    @BeforeAll
    static void setUp() {
        reader = new FruitFileDaoReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile(TEST_FILE_NAME, FILE_EXTENSION);
        Files.writeString(tempFile, VALID_FILE_CONTENT);
        List<String> fileLines = reader.read(tempFile.toString());
        assertEquals(EXPECTED_VALID_FILE_LINES, fileLines);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_emptyFile_ok() throws IOException {
        Path tempFile = Files.createTempFile(EMPTY_FILE_NAME, FILE_EXTENSION);
        List<String> fileLines = reader.read(tempFile.toString());
        assertTrue(fileLines.isEmpty());
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(NON_EXISTENT_FILE_PATH));
    }

    @Test
    void read_largeFile_ok() throws IOException {
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < LARGE_FILE_LINES_COUNT; i++) {
            largeContent.append(LARGE_FILE_PREFIX).append(i).append("\n");
        }
        Path tempFile = Files.createTempFile(LARGE_FILE_NAME, FILE_EXTENSION);
        Files.writeString(tempFile, largeContent.toString());
        List<String> fileLines = reader.read(tempFile.toString());
        assertEquals(LARGE_FILE_LINES_COUNT, fileLines.size());
        assertEquals(LARGE_FILE_PREFIX + 0, fileLines.get(0));
        assertEquals(LARGE_FILE_PREFIX + (LARGE_FILE_LINES_COUNT - 1),
                fileLines.get(LARGE_FILE_LINES_COUNT - 1));
        Files.deleteIfExists(tempFile);
    }
}
