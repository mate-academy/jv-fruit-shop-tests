package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
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
    public void readFromFile_PathToFileIsValid_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual =
                fileReaderService.readFromFile("src/test/resources/transaction.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_PathToFileIsNotValid_NotOk() {
        try {
            fileReaderService.readFromFile("src/test/resources/transaction");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown - path to File is not valid");
    }

    @Test
    public void readFromEmptyFile() {
        List<String> empty =
                fileReaderService.readFromFile("src/test/resources/empty.csv");
        Assert.assertTrue(empty.isEmpty());
    }
}
