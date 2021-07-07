package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/testValidData.csv";
    private static final String FILE_NAME = "testValidData.csv";
    private static List<String> expected;

    @BeforeClass
    public static void operationBeforeTest() {
        expected = new ArrayList<>();
    }

    @After
    public void operationAfterTest() {
        expected.clear();
    }

    @Test
    public void readerFromFile_validPath_Ok() {
        expected = lineWithFile();
        service.FileReader readWithFile = new FileReaderImpl();
        List<String> actual = readWithFile.readFromFile(PATH_TO_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerFromFile_notValidPath_NotOk() {
        service.FileReader readWithFile = new FileReaderImpl();
        readWithFile.readFromFile(FILE_NAME);
    }

    private List<String> lineWithFile() {
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
