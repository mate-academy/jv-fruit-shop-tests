package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String FILE_PATH = "src/main/resources/input.csv";
    private static final String EMPTY_FILE = "src/main/resources/emptyFile.csv";
    private static final String EXISTENT = "src/main/resources/existent.csv";
    private static List<String> expected;
    private static List<String> actual;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
        expected = new ArrayList<>();
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
    public void getDataFromFile_RightPath_ok() {
        expected = inputData;
        actual = fileReader.read(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readEmptyFile() {
        fileReader.read(EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFromExistentFile() {
        fileReader.read(EXISTENT);
    }
}
