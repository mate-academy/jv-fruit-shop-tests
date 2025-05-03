package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String INPUT_OK_FILE_PATH = "src/test/resources/inputOkFile.csv";
    private static final String INPUT_NOT_OK_FILE_PATH = "src/test/resources/notOkFile.csv";
    private static final String INPUT_EMPTY_FILE_PATH = "src/test/resources/inputEmptyFile.csv";
    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_okFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                                        "b,banana,20","b,apple,100","s,banana,100","p,banana,13");
        List<String> actual = reader.readFromFile(INPUT_OK_FILE_PATH);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notOkFile_notOk() {
        reader.readFromFile(INPUT_NOT_OK_FILE_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_withNullPath_notOk() {
        reader.readFromFile(null);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> dataFromFile = reader.readFromFile(INPUT_EMPTY_FILE_PATH);
        int expected = 0;
        int actual = dataFromFile.size();
        Assert.assertEquals(expected,actual);
    }
}
