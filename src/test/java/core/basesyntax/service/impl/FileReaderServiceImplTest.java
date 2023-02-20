package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH =
            "src/test/resources/fruit-shop-activities.csv";
    private static final String NON_EXISTING_FILE_PATH =
            "src/test/resources/random.csv";
    private static final List<String> CORRECT_INPUT = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_noSuchFile_notOk() {
        fileReaderService.readFromFile(NON_EXISTING_FILE_PATH);
    }

    @Test
    public void readFromFile_successfulReading_ok() {
        List<String> actual = fileReaderService.readFromFile(CORRECT_FILE_PATH);
        Assert.assertEquals(CORRECT_INPUT, actual);
    }
}
