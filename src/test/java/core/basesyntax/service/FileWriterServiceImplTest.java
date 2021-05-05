package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.FileReaderService;
import core.basesyntax.service.interfaces.FileWriterService;
import java.util.List;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private final String incorrectPath = "main/resources/fail";
    private final String correctPath = "src/test/resources/writeTest.csv";

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeToIncorrectPath_NotOk() {
        fileWriterService.writeToFile("", incorrectPath);
    }

    @Test
    public void writeToFile_writeToCorrectPath_Ok() {
        fileWriterService.writeToFile("Hello World", correctPath);
        List<String> actual = fileReaderService.readFromFile(correctPath);
        List<String> expected = List.of("Hello World");
        assertEquals(expected, actual);
    }
}
