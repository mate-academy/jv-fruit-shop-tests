package core.basesyntax.service.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final Path TEST_FILE_PATH = Path.of("src/test/resources/writetest.csv");
    private static final String TEST_WORD = "test";
    private FileWriterService fileWrite;

    @BeforeEach
    void setUp() {
        fileWrite = new FileWriterServiceImpl();
    }

    @Test
    void writeToFile_validRecord_Success() throws IOException {
        fileWrite.writeToFile(TEST_WORD, String.valueOf(TEST_FILE_PATH));

        try (BufferedReader reader = Files.newBufferedReader(TEST_FILE_PATH)) {
            String line = reader.readLine();
            assertEquals(TEST_WORD, line);
        }
    }

    @Test
    void writeToFile_emptyRecord_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileWrite.writeToFile("", String.valueOf(TEST_FILE_PATH));
        });

        assertEquals("Record cannot be null or empty", exception.getMessage());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
    }
}
