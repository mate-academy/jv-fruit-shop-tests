package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/TransactionsPerDay.csv";
    private static final String WRONG_FILE_PATH = "someNonExistingFolder/someNonExistingFile.xyz";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_correctPath_ok() {
        String[] testDataArray = new String[] {"type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"};
        List<String> expectedData = new ArrayList<>(Arrays.asList(testDataArray));
        List<String> dataFromFile = fileReaderService.readFromFile(CORRECT_FILE_PATH);
        Assert.assertEquals(expectedData, dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_incorrectPath_notOk() {
        fileReaderService.readFromFile(WRONG_FILE_PATH);
    }
}
