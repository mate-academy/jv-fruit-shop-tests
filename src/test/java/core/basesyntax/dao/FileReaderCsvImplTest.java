package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderCsvImplTest {
    private static final String FILE_NAME = "src\\test\\resources\\inputFileTest.csv";
    private static final String EMPTY_FILE = "src\\test\\resources\\InputEmptyDataFileTest.csv";
    private static final String MISSING_FILE_NAME = "src\\test\\resources\\MissingFile.csv";
    private static FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderCsvImpl();
    }

    @Test
    public void getData_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        List<String> actualList = fileReader.getData(FILE_NAME);
        assertEquals("Test failed: incorrect title",expectedList.get(0), actualList.get(0));
        assertEquals("Test failed: incorrect number of lines",
                expectedList.size(),actualList.size());
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromMissingFile_NotOk() {
        fileReader.getData(MISSING_FILE_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromEmptyFile_NotOk() {
        fileReader.getData(EMPTY_FILE);
    }

    @Test(expected = ValidationException.class)
    public void getDataFromNullFile_NotOk() {
        fileReader.getData(null);
    }
}
