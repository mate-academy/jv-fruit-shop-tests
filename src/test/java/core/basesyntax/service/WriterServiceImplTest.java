package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
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
        writerService.writeToFile(expectedText, OUTPUT_FILE_PATH);
        List<String> actualResult = readerService.read(OUTPUT_FILE_PATH);
        String actualText = String.join(System.lineSeparator(), actualResult);
        Assertions.assertEquals(actualText, expectedText);
    }

    @Test
    public void writeToFile_writeToEmptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                writerService.writeToFile("expectedText", ""));
    }
}

