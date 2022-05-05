package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/input.csv";
    private static final String INVALID_PATH = "src/test/resources/invalidInput.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static FileReaderService fileReader;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
        inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,100");
        inputData.add("b,apple,100");
        inputData.add("p,banana,25");
        inputData.add("s,banana,25");
        inputData.add("r,banana,200");
        inputData.add("p,banana,25");
    }

    @Test
    public void readFile_isOk() {
        List<String> expected = inputData;
        List<String> actual = fileReader.read(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_notOk() {
        List<String> actual = fileReader.read(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void read_nullFileName_NotOk() {
        fileReader.read(null);
    }

    @Test
    public void readFile_emptyFile() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected,actual);
    }
}
