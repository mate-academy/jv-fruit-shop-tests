package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitFileDaoWriterImplTest {

    private static FruitFileDaoWriterImpl writer;

    private static final String VALID_CONTENT = "Test content";
    private static final String INITIAL_CONTENT = "Initial content";
    private static final String NEW_CONTENT = "New content";
    private static final String INVALID_FILE_PATH = "/invalidPath/testFile.txt";
    private static final String TEST_FILE_NAME = "testFile";
    private static final String FILE_EXTENSION = ".txt";
    private static final String EMPTY_FILE_NAME = "";

    @BeforeAll
    static void setUp() {
        writer = new FruitFileDaoWriterImpl();
    }

    @Test
    void write_validContent_ok() throws IOException {
        Path tempFile = Files.createTempFile(TEST_FILE_NAME, FILE_EXTENSION);
        writer.write(VALID_CONTENT, tempFile.toString());
        String fileContent = Files.readString(tempFile);
        assertEquals(VALID_CONTENT, fileContent);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_emptyFileName_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write(VALID_CONTENT, EMPTY_FILE_NAME));
    }

    @Test
    void write_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write(VALID_CONTENT, INVALID_FILE_PATH));
    }

    @Test
    void write_existingFile_ok() throws IOException {
        Path tempFile = Files.createTempFile(TEST_FILE_NAME, FILE_EXTENSION);
        Files.writeString(tempFile, INITIAL_CONTENT);
        writer.write(NEW_CONTENT, tempFile.toString());
        String fileContent = Files.readString(tempFile);
        assertEquals(NEW_CONTENT, fileContent);
        Files.deleteIfExists(tempFile);
    }
}
