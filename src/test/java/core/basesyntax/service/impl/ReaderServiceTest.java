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

    @Test
    public void read_nullFile_notOk() {
        try {
            readerService.read(null);
            Assert.fail("Expected NullPointerException");
        } catch (RuntimeException e) {
            Assert.assertEquals("Can't read file null", e.getMessage());
        }
    }

    @Test
    public void read_emptyFile_sizeIsNull() {
        List<String> actual = readerService.read(EMPTY_FILE_NAME);
        Assert.assertEquals(0, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void read_notExistedFile_notOk() {
        readerService.read(NOT_EXISTED_FILE_NAME);
    }

    @Test
    public void read_file_ok() {
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
