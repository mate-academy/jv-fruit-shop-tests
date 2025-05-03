package core.basesyntax.services.impl;

import core.basesyntax.services.FileReaderService;
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
    public void fileReaderServiceCustom_OK() {
        String randomContentPath = "src/test/resources/testInput.csv";
        List<String> expected = List.of("cherry", "apple", "banana");
        List<String> actual = fileReaderService.read(randomContentPath);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderServiceNotExistingFile_notOK() {
        String invalidFilePath = "src/test/resources/someFilecsv";
        fileReaderService.read(invalidFilePath);
    }

    @Test
    public void fileReaderServiceValidData_OK() {
        String validReportPath = "src/test/resources/TestNotEmptyFile.csv";
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.read(validReportPath);
        Assert.assertEquals(expected, actual);
    }
}
