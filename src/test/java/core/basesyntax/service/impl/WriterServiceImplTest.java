package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String WRITE_FILE_PATH_OK
            = "src/main/java/core/basesyntax/resuorse/existing_file.csv";
    private static final String INVALID_WRITE_FILE_PATH
            = "invalid/path/existing_file.csv";
    private static final String WRITE_FILE_PATH_NULL = null;
    private static final String WRITE_FILE_PATH_EMPTY = "";
    private static final String FILE_DATA_OK = "banana,20" + System.lineSeparator()
            + "apple,100";
    private static final String FILE_DATA_NULL = null;
    private static final String FILE_DATA_EMPTY = "";
    private WriterService writeService;

    @BeforeEach
    public void setUp() {
        writeService = new WriterServiceImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(WRITE_FILE_PATH_OK));
    }

    @Test
    public void test_WriteToFile_ValidData_ValidFilePath_Ok() throws IOException {
        writeService.writeToFile(WRITE_FILE_PATH_OK, FILE_DATA_OK);
        String actual = Files.readString(Path.of(WRITE_FILE_PATH_OK));
        assertEquals(FILE_DATA_OK, actual);
    }

    @Test
    public void test_WriteToFile_InvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(INVALID_WRITE_FILE_PATH, FILE_DATA_OK));
    }

    @Test
    public void test_WriteToFile_NullFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(WRITE_FILE_PATH_NULL, FILE_DATA_OK));
    }

    @Test
    public void test_WriteToFile_EmptyFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(WRITE_FILE_PATH_EMPTY, FILE_DATA_OK));
    }

    @Test
    public void test_WriteToFile_NullData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(WRITE_FILE_PATH_OK, FILE_DATA_NULL));
    }

    @Test
    public void test_WriteToFile_EmptyData_ValidFilePath_Ok() throws IOException {
        writeService.writeToFile(WRITE_FILE_PATH_OK, FILE_DATA_EMPTY);
        String actual = Files.readString(Path.of(WRITE_FILE_PATH_OK));
        assertEquals(FILE_DATA_EMPTY, actual);
    }
}
