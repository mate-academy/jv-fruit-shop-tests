package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String WRONG_FILE_NAME = "src/test/resoures/wrongtest.csv";
    private static final String OUTPUT_FILE_NAME = "src/test/resources/result.csv";
    private static final int ELEMENT_INDEX_TO_COMPARE = 0;
    private static WriterService writerService;
    private static final String EXPECTED_RESULT = "fruits,quantity" + System.lineSeparator()
            + "banana,9" + System.lineSeparator()
            + "apple,27";

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeToFile_NotOk() {
        writerService.writeToFile(EXPECTED_RESULT, WRONG_FILE_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_nullReport_NotOk() {
        writerService.writeToFile(null, OUTPUT_FILE_NAME);
    }

    @Test
    public void writerService_writeToFile_isOk() {
        String actual;
        String expected = "fruit,quantity";
        writerService.writeToFile(expected, OUTPUT_FILE_NAME);
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_NAME)).get(ELEMENT_INDEX_TO_COMPARE);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file with name: " + OUTPUT_FILE_NAME, e);
        }
        Assert.assertEquals(expected, actual);
    }
}
