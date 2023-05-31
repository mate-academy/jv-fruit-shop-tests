package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.NoSuchFileException;
import core.basesyntax.service.FileReaderService;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private static final String VALID_PATH = "src/test/resources/test.csv";

    @Test
    public void readFromFile_ValidPath_ok() {
        String actual = fileReaderService.readFromFile(VALID_PATH);
        String expected = "Hello, my friend! Approve this task, please)"
                + System.lineSeparator() + "-_-";
        assertEquals("data from " + VALID_PATH + " should be read", expected, actual);
    }

    @Test(expected = NoSuchFileException.class)
    public void readFromFile_InvalidPath_notOk() {
        fileReaderService.readFromFile(VALID_PATH + "/notExist");
    }

    @Test(expected = NoSuchFileException.class)
    public void readFromFile_NullPath_notOk() {
        fileReaderService.readFromFile(null);
    }
}
