package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static String EMPTY_FILE_PATH;
    private static String NOT_EMPTY_FILE_PATH;
    private static String NOT_EXIST_FILE_PATH;
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        EMPTY_FILE_PATH = "src/test/resources/empty.csv";
        NOT_EMPTY_FILE_PATH = "src/test/resources/fruits.csv";
        NOT_EXIST_FILE_PATH = "src/test/resources/file_exist.csv";
        reader = new ReaderImpl();
    }

    @Test
    void readFromFile_emptyFile_ok() {
        List<String> list = reader.readFromFile(EMPTY_FILE_PATH);
        assertTrue(list.isEmpty());
    }

    @Test
    void readFromFile_notEmptyFile_notOk() {
        List<String> list = reader.readFromFile(NOT_EMPTY_FILE_PATH);
        assertFalse(list.isEmpty());
    }

    @Test
    void readFromFile_fileDoesNotExist_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(NOT_EXIST_FILE_PATH));
    }
}
