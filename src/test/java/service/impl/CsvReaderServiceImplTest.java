package service.impl;

import org.junit.Test;
import service.CsvReaderService;
import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "src/test/java/test_files/emptyFile.csv";
    private static final String DEFAULT_FILE_NAME = "src/test/java/test_files/defaultFile.csv";
    private static final String NO_FILE_NAME = "";
    private static final CsvReaderService readerService = new CsvReaderServiceImpl();
    private static final String[] ACTUAL_CONTENT = """
            b,banana,20
            b,apple,100
            s,banana,100
            p,banana,13
            r,apple,10
            p,apple,20
            p,banana,5
            s,banana,50
            b,kiwi,10""".split(System.lineSeparator());

    @Test
    public void defaultFile_Ok() {
        assertArrayEquals(ACTUAL_CONTENT,
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
