package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static List<String> inputData;
    private static String filePath;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
        inputData = new ArrayList<>();
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
    public void getFileData_correctData_ok() {
        filePath = "src/test/resources/input.csv";
        List<String> expected = inputData;
        List<String> actual = fileReader.getFileData(filePath);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getFileData_incorrectPath_notOk() {
        filePath = "src/main/resources/input1.csv";
        List<String> actual = fileReader.getFileData(filePath);
    }
}
