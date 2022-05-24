package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private static final String BAD_FILE_PATH = "/";
    private static final String DATA = "fruit,quantity" + System.lineSeparator()
            + "apple,10" + System.lineSeparator()
            + "banana,20" + System.lineSeparator();
    private static WriterService writerService;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Before
    public void setUp() {
        exception = new Exception();
    }

    @Test
    public void writeToFile_nullFilePath_notOK() {
        try {
            writerService.writeToFile(null, DATA);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void writeToFile_nullData_notOK() {
        try {
            writerService.writeToFile(FILE_PATH, null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void writeToFile_badFilePath_notOK() {
        try {
            writerService.writeToFile(BAD_FILE_PATH, DATA);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void writeToFile_goodInput_OK() {
        try {
            writerService.writeToFile(FILE_PATH, DATA);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
    }
}
