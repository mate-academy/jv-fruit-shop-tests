package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_PATH = "src/test/resources/inputFile.csv";
    private static final String INVALID_PATH = "src/test/resources/wrong.csv";
    private static final String EMPTY_FILE = "src/test/resources/inputEmptyFile.csv";
    private static FileReaderImpl fileReader;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
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
    public void readFromFile_isOk() {
        List<String> excepted = inputData;
        List<String> actual = fileReader.getDataFromFile(VALID_PATH);
        Assert.assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_incorrectData_isNotOk() {
        List<String> actual = fileReader.getDataFromFile(INVALID_PATH);
    }

    @Test
    public void readFromFile_FromEmptyFile_isOk() {
        List<String> excepted = new ArrayList<>();
        List<String> actual = fileReader.getDataFromFile(EMPTY_FILE);
        Assert.assertEquals(excepted, actual);
    }
}
