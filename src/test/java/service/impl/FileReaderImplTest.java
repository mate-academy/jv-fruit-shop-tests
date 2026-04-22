package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid_input.csv";
    private static final String INVALID_PATH = "non_existent.csv";
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        List<String> actual = fileReader.read(VALID_FILE_PATH);
        assertEquals(4, actual.size()); // Наприклад, заголовок + 3 рядки
    }

    @Test
    void read_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.read(INVALID_PATH));
    }
}
