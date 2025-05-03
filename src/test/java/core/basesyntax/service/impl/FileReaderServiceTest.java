package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;
    private static final String VALID_FILE_PATH = "src/test/resources/transaction.csv";

    @BeforeClass
    public static void init() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_pathToFileIsValid_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual =
                fileReaderService.readFromFile(VALID_FILE_PATH);
        Assert.assertEquals("Expected " + expected + ", but was "
                + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_pathToFileIsNotValid_notOk() {
        fileReaderService.readFromFile("src/test/resources/transaction");
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> empty =
                fileReaderService.readFromFile("src/test/resources/empty.csv");
        Assert.assertTrue("Expected empty List<String> for empty file, but was "
                + empty, empty.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_pathToFileIsNull_notOk() {
        fileReaderService.readFromFile(null);
    }
}
