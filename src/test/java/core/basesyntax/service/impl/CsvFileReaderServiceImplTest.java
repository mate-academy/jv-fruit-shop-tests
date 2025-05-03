package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String FROM_FILE = "src/main/resources/input.csv";
    private static final String FROM_EMPTY_FILE = "src/main/resources/empty_input.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String INVALID_PATH = "invalid path";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void read_validData_ok() {
        String expected = "b,banana,20" + SEPARATOR
                + "b,apple,100" + SEPARATOR
                + "s,banana,100" + SEPARATOR
                + "p,banana,13" + SEPARATOR
                + "r,apple,10" + SEPARATOR
                + "p,apple,20" + SEPARATOR
                + "p,banana,5" + SEPARATOR
                + "s,banana,50" + SEPARATOR;
        String actual = readerService.read(FROM_FILE);
        assertEquals("The read method should return: " + expected, expected, actual);
    }

    @Test
    public void read_emptyFile_ok() {
        boolean actual = readerService.read(FROM_EMPTY_FILE)
                .isEmpty();
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        readerService.read(INVALID_PATH);
    }
}
