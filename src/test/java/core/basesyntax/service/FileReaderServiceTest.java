package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromExistentFile_Ok() {
        List<String> actualResult =
                fileReaderService.readFromFile("src/main/resources/report_test.csv");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("fruit, quantity");
        expectedResult.add("report");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullPath_NotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidPath_NotOk() {
        String pathToFile = "fruits";
        fileReaderService.readFromFile(pathToFile);
    }
}
