package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE_NAME = "src/test/resources/test_data.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources/data.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/test_empty_data.csv";
    private ReaderService readerService;

    @Before
    public void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_OK() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("test file should contains");
        expectedList.add("at least two lines");
        List<String> actualList = readerService.readFromFile(INPUT_FILE_NAME);
        int expected = 2;
        int actual = 0;
        for (int i = 0; i < actualList.size(); i++) {
            if (actualList.get(i).equals(expectedList.get(i))) {
                actual++;
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    public void readFromEmptyFile_OK() {
        int expected = 0;
        int actual;
        List<String> actualList = readerService.readFromFile(EMPTY_FILE_NAME);
        actual = actualList.size();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void wrongFileName_NotOK() {
        readerService.readFromFile(WRONG_FILE_NAME);
    }

    @Test (expected = RuntimeException.class)
    public void emptyFileName_NotOK() {
        readerService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void nullFileName_NotOK() {
        readerService.readFromFile(null);
    }
}
