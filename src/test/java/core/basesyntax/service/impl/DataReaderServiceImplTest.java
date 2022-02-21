package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.DataReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataReaderServiceImplTest {

    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final String INCORRECT_FILE_PATH = "some incorrect path";
    private static final String TEST_FEW_LINES = "src/test/resources/testInput.csv";
    private DataReaderService dataReaderService;

    @Before
    public void init() {
        dataReaderService = new DataReaderServiceImpl();
    }

    @Test
    public void readFromFile_correctPath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = dataReaderService.readDataFromFile(TEST_FEW_LINES);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nullPath_notOk() {
        dataReaderService.readDataFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_incorrectPath_notOk() {
        dataReaderService.readDataFromFile(INCORRECT_FILE_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyFile_notOk() {
        dataReaderService.readDataFromFile(EMPTY_FILE_PATH);
    }
}
