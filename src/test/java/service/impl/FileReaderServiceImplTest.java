package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileReaderService;

public class FileReaderServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/input.csv";
    private static final String INVALID_PATH = "src/test/resources/inpu.csv";
    private static final String EMPTY_FILE = "src/test/resources/inputEmpty.csv";
    private static FileReaderService fileReader;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
        inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("b,apple,100");
        inputData.add("s,banana,100");
        inputData.add("p,banana,13");
        inputData.add("r,apple,10");
        inputData.add("p,apple,20");
        inputData.add("p,banana,5");
        inputData.add("s,banana,50");
    }

    @Test
    public void readFile_correctData_isOk() {
        List<String> expected = inputData;
        List<String> actual = fileReader.getFileData(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_incorrectData_notOk() {
        List<String> actual = fileReader.getFileData(INVALID_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void readFile_nullPath_NotOk() {
        fileReader.getFileData(null);
    }

    @Test
    public void readFile_emptyFile() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.getFileData(EMPTY_FILE);
        assertEquals(expected,actual);
    }
}
