package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/file.csv";
    private static final String BAD_FILE_PATH = "src/test/resources/file1.csv";
    private static ReaderService readerService;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Before
    public void setUp() {
        exception = new Exception();
    }

    @Test
    public void readFromFile_nullFile_notOK() {
        try {
            readerService.readFromFile(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void readFromFile_badFilePath_notOK() {
        try {
            readerService.readFromFile(BAD_FILE_PATH);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void readFromFile_goodFilePath_OK() {
        final String expectedString1 = "type,fruitTransaction,quantity";
        final String expectedString2 = "b,banana,20";
        final String expectedString3 = "b,apple,100";
        List<String> lines = new ArrayList<>();
        try {
            lines = readerService.readFromFile(FILE_PATH);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(3, lines.size());
        Assert.assertEquals(expectedString1, lines.get(0));
        Assert.assertEquals(expectedString2, lines.get(1));
        Assert.assertEquals(expectedString3, lines.get(2));
    }
}
