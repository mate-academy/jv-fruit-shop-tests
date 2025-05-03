package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    public static final String FILE_PATH = "src/test/resources/database.csv";
    private static FileReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_WrongFilePath_NotOk() {
        readerService.read("wrong path");
    }

    @Test(expected = RuntimeException.class)
    public void read_Null_NotOk() {
        readerService.read(null);
    }

    @Test
    public void read_ValidInput_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = readerService.read(FILE_PATH);
        Assert.assertEquals(actual, expected);
    }
}
