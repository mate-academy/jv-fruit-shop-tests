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
    private static final String EXPECTED_STRING1 = "type,fruitTransaction,quantity";
    private static final String EXPECTED_STRING2 = "b,banana,20";
    private static final String EXPECTED_STRING3 = "b,apple,100";
    private static ReaderService readerService;
    private static List<String> stringList;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        stringList = new ArrayList<>();
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
        try {
            stringList = readerService.readFromFile(FILE_PATH);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(3, stringList.size());
        Assert.assertEquals(EXPECTED_STRING1, stringList.get(0));
        Assert.assertEquals(EXPECTED_STRING2, stringList.get(1));
        Assert.assertEquals(EXPECTED_STRING3, stringList.get(2));
    }
}
