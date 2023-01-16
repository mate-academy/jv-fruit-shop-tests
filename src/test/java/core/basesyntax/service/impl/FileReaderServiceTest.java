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
    public void readFromFile_pathToFileIsValid_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual =
                fileReaderService.readFromFile("src/test/resources/transaction.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_pathToFileIsNotValid_notOk() {
        try {
            fileReaderService.readFromFile("src/test/resources/transaction");
        } catch (RuntimeException e) {
            throw new RuntimeException("RuntimeException should be thrown - "
                    + "path to File is not valid", e);
        }
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> empty =
                fileReaderService.readFromFile("src/test/resources/empty.csv");
        Assert.assertTrue(empty.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_nullFile_ok() {
        List<String> empty =
                fileReaderService.readFromFile(null);
    }
}
