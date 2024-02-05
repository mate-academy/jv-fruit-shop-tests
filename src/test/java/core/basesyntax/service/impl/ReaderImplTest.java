package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final String NOT_EMPTY_FILE_PATH = "src/test/resources/fruits.csv";
    private static final String NOT_EXIST_FILE_PATH = "src/test/resources/file_exist.csv";
    private final Reader reader = new ReaderImpl();

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
