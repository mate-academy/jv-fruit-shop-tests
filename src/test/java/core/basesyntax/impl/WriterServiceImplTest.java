package core.basesyntax.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";
    private WriterServiceImpl writerService;
    private ReaderServiceImpl readerService;

    @BeforeEach
    public void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void writeToFile_rightPath_Ok() {
        String expectedText = "banana,154"
                + System.lineSeparator()
                + "apple,90";
        writerService.writeToFile(expectedText, REPORT_FILE_PATH);
        List<String> actualResult = readerService.readData(REPORT_FILE_PATH);
        String actualText = String.join(System.lineSeparator(), actualResult);
        Assertions.assertEquals(actualText, expectedText);
    }

    @Test
    public void writeToFile_writeToEmptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                writerService.writeToFile("expectedText", ""));
    }
}
