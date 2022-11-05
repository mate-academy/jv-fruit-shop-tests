package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String EMPTY_FILE_NAME = "src/test/resources/empty.csv";
    private static final String NOT_EXISTED_FILE_NAME = "src/test/resources/notExistedFile.csv";
    private static final String EXISTED_FILE_NAME = "src/test/resources/input.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void readerServiceInitialization() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readNullFile_notOk() {
        readerService.read(null);
    }

    @Test
    public void readEmptyFile_sizeIsNull() {
        List<String> actual = readerService.read(EMPTY_FILE_NAME);
        Assert.assertEquals(0, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void readNotExistedFile_notOk() {
        readerService.read(NOT_EXISTED_FILE_NAME);
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = readerService.read(EXISTED_FILE_NAME);
        Assert.assertEquals(expected, actual);
    }
}
