package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import service.CsvReaderService;

public class CsvReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "src/test/java/test_files/emptyFile.csv";
    private static final String DEFAULT_FILE_NAME = "src/test/java/test_files/defaultFile.csv";
    private static final String NO_FILE_NAME = "";
    private static final CsvReaderService READER_SERVICE = new CsvReaderServiceImpl();

    @Test
    public void defaultFile_Ok() {
        assertEquals(9,
                READER_SERVICE.readFromFile(DEFAULT_FILE_NAME).length,
                "Expected array length is 9");
    }

    @Test
    public void noFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                READER_SERVICE.readFromFile(NO_FILE_NAME));
    }

    @Test
    public void emptyFile_Ok() {
        assertEquals(0,
                READER_SERVICE.readFromFile(EMPTY_FILE_NAME).length,
                "Expected array length is 0");
    }

}
