package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderCsvTest {
    private static final String DEFAULT_VALID_FILE = "src/main/resources/example.csv";
    private static final String INVALID_PATH_TO_FILE = "src/main/resources/example3.csv";
    private FileReaderCsv fileReaderCsv = new FileReaderCsv();

    @Test
    void read_validPath_Ok() {
        List<String> actual = fileReaderCsv.read(DEFAULT_VALID_FILE);
        assertEquals(actual, fileReaderCsv.read(DEFAULT_VALID_FILE));
    }

    @Test
    void read_invalidPath_ThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () ->
                fileReaderCsv.read(INVALID_PATH_TO_FILE), "Can't read file "
                + INVALID_PATH_TO_FILE);
    }
}
