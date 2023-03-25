package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private WriterServiceImpl writerService;
    private ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void writerService_write_Ok() {
        String expectedText = "banana,154"
                + System.lineSeparator()
                + "apple,90";
        writerService.writeToFile(expectedText, OUTPUT_FILE_PATH);
        List<String> actualResult = readerService.read(OUTPUT_FILE_PATH);
        String actualText = String.join(System.lineSeparator(), actualResult);
        Assert.assertEquals("Test failed! You should returned next text "
                + expectedText + " but you returned "
                + actualText, actualText, expectedText);
    }

    @Test(expected = RuntimeException.class)
    public void writeToEmptyPath_notOk() {
        writerService.writeToFile("expectedText", "");
    }
}

