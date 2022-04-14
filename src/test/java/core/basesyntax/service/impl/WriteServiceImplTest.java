package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceImplTest {
    private static WriterService writerService;
    private static final String CORRECT_PATH = "src/test/java/resources/report.csv";
    private static final String INCORRECT_PATH = "/java!/resources/shop_operation.csv";
    private static final String FRUIT_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriteServiceImpl();
    }

    @Test
    public void writeCorrectInputFile_Ok() {
        String report = "s,banana,100" + System.lineSeparator();
        writerService.write(report,CORRECT_PATH);
        Assert.assertNotNull(report);
        Assert.assertNotNull(CORRECT_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileWithWrongFilePath_NotOk() {
        writerService.write(FRUIT_DATA, INCORRECT_PATH);
    }
}
