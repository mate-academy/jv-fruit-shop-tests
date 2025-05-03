package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/file.csv";
    private static final String BAD_FILE_PATH = "src/test/resources/file1.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_nullFile_notOK() {
        try {
            readerService.readFromFile(null);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void readFromFile_badFilePath_notOK() {
        try {
            readerService.readFromFile(BAD_FILE_PATH);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void readFromFile_goodFilePath_OK() {
        List<String> lines = readerService.readFromFile(FILE_PATH);
        Assert.assertEquals(3, lines.size());
        Assert.assertEquals("type,fruitTransaction,quantity", lines.get(0));
        Assert.assertEquals("b,banana,20", lines.get(1));
        Assert.assertEquals("b,apple,100", lines.get(2));
    }
}
