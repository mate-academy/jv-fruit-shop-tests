package core.basesyntax.services.implementation;

import core.basesyntax.services.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_INPUT_FILE = "src/main/resources/fruits.csv";
    private static final String PATH_TO_NONEXIST_FILE = "src/main/resources/nonexist.csv";
    private static List<String> fruitInfo;
    private static FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderImpl();
        fruitInfo = new ArrayList<>();
        fruitInfo.add("type,fruit,quantity");
        fruitInfo.add("b,banana,20");
        fruitInfo.add("b,apple,100");
        fruitInfo.add("s,banana,100");
        fruitInfo.add("p,banana,13");
        fruitInfo.add("r,apple,10");
        fruitInfo.add("p,apple,20");
        fruitInfo.add("p,banana,5");
        fruitInfo.add("s,banana,50");
    }

    @Test
    public void readData_readFromFile_ok() {
        List<String> expectedList = fruitInfo;
        List<String> actualList = fileReaderService.read(PATH_TO_INPUT_FILE);
        Assert.assertEquals("Test failed. The files are different.", expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readData_FromNonExistingFile_notOk() {
        fileReaderService.read(PATH_TO_NONEXIST_FILE);
    }
}
