package core.basesyntax.service.implementations;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/data_test_ok.csv";
    private static final String PATH_TO_NOT_EXISTING_FILE = "src/test/resources/casper.csv";
    private static FileReaderService fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void fileReaderServiceImplTest_readFromFile_Ok() {
        List<String> actual = fileReader.readFromFile(PATH_TO_FILE);
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fileReaderServiceImplTest_readFromNonExistFile_NotOk() {
        fileReader.readFromFile(PATH_TO_NOT_EXISTING_FILE);
    }
}
