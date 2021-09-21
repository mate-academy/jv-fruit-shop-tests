package core.basesyntax;

import core.basesyntax.service.files.FileReader;
import core.basesyntax.service.files.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderTest {
    private static final String INPUT_FILE = "src/main/resources/fruits_correct.csv";
    private static final String NON_EXISTENT_FILE = "src/main/resource/non_existent.csv";
    private static List<String> fileData;
    private static FileReader fileReaderImpl;

    @Before
    public void setUp() {
        fileReaderImpl = new FileReaderImpl();
        fileData = new ArrayList<>();
        fileData.add("b,banana,20");
        fileData.add("b,apple,100");
        fileData.add("s,banana,100");
        fileData.add("p,banana,13");
        fileData.add("r,apple,10");
        fileData.add("p,apple,20");
        fileData.add("p,banana,5");
        fileData.add("s,banana,50");
    }

    @Test
    public void readDataFromFile_readFile_OK() {
        List<String> expectedFileData = fileData;
        List<String> actualFileData = fileReaderImpl.readFile(INPUT_FILE);
        Assert.assertEquals("Test failed! Expected file data and actual file data is different!",
                expectedFileData, actualFileData);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromNonExistentFile_readFile_Not_OK() {
        fileReaderImpl.readFile(NON_EXISTENT_FILE);
    }
}
