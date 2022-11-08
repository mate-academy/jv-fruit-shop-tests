package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromCsvServiceTest {
    private static final String EMPTY_FILE_NAME = "src/test/resources/empty_input.csv";
    private static final String FIRST_INPUT_FILE_NAME = "src/test/resources/first_input.csv";
    private static final String FIRST_LINE_IN_FILE = "b,banana,20";
    private static final String SECOND_LINE_IN_FILE = "b,apple,100";
    private static final int SIZE_OF_EMPTY_LIST = 0;

    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderFromCsvService();
    }

    @Test
    public void read_fileWithoutDate_ok() {
        List<String> actual = readerService.readData(EMPTY_FILE_NAME);
        assertEquals(SIZE_OF_EMPTY_LIST, actual.size());
    }

    @Test
    public void read_fileWithSomeData_ok() {
        List<String> actual = readerService.readData(FIRST_INPUT_FILE_NAME);
        assertEquals(List.of(FIRST_LINE_IN_FILE, SECOND_LINE_IN_FILE), actual);
    }
}
