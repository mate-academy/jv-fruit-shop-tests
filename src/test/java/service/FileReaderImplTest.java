package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/testValidData.csv";
    private static final String INVALID_FILE_NAME = "testValidData.csv";
    private static List<String> expected;
    private static FileReader reader;

    @BeforeClass
    public static void operationBeforeTest() {
        expected = new ArrayList<>();
        expected = getExpectedResult();
        reader = new FileReaderImpl();
    }

    @Test
    public void readerFromFile_validPath_Ok() {
        List<String> actual = reader.readFromFile(PATH_TO_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerFromFile_notValidPath_NotOk() {
        reader.readFromFile(INVALID_FILE_NAME);
    }

    private static List<String> getExpectedResult() {
        List<String> resultList = new ArrayList<>();
        resultList.add("type,fruit,quantity");
        resultList.add("b,banana,20");
        resultList.add("b,apple,100");
        resultList.add("s,banana,100");
        resultList.add("p,banana,13");
        resultList.add("r,apple,10");
        resultList.add("p,apple,20");
        resultList.add("p,banana,5");
        resultList.add("s,banana,50");
        return resultList;
    }
}
