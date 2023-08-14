package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT_FILE = "result.csv";
    private WriterServiceImpl writerService;
    private ReaderServiceImpl readerService;

    @BeforeEach
    public void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void writeRightFile_Ok() {
        String expectedText = "banana,154"
                + System.lineSeparator()
                + "apple,90";
        writerService.writeToFile(OUTPUT_FILE, "banana,154"
                + System.lineSeparator()
                + "apple,90");
        List<String> actualResult = readerService.readFromFile(OUTPUT_FILE);
        String actualText = String.join(System.lineSeparator(), actualResult);
        Assertions.assertEquals(actualText, expectedText);
    }

    @Test
    public void writeToFileEmptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                writerService.writeToFile("", "banana,154"
                        + System.lineSeparator()
                        + "apple,90"));
    }
}
