package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderServiceImpl fileReader;
    private static List<String> inputData;
    private static String filePath;

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
        filePath = "src/test/resources/input.csv";
        List<String> expected = inputData;
        List<String> actual = fileReader.getFileData(filePath);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_incorrectData_notOk() {
        filePath = "src/test/resources/inpu.csv";
        List<String> actual = fileReader.getFileData(filePath);
    }
}
