package service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import service.CsvReaderService;

public class CsvReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "src/test/java/test_files/emptyFile.csv";
    private static final String DEFAULT_FILE_NAME = "src/test/java/test_files/defaultFile.csv";
    private static final String NO_FILE_NAME = "";
    private static final CsvReaderService readerService = new CsvReaderServiceImpl();
    private static final String ACTUAL_CONTENT = "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50\n"
            + "b,kiwi,10";

    @Test
    public void defaultFile_Ok() {
        assertArrayEquals(ACTUAL_CONTENT.split(System.lineSeparator()),
                readerService.readFromFile(DEFAULT_FILE_NAME),
                "Actual content differs from reader work result");
    }

    @Test
    public void noFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(NO_FILE_NAME));
    }

    @Test
    public void emptyFile_Ok() {
        assertEquals(0,
                readerService.readFromFile(EMPTY_FILE_NAME).length,
                "Expected array length is 0");
    }
}
