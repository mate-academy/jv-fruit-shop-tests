package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.FileReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validPath_ok() {
        String expected = "some test string";
        String actual = fileReaderService
                .readFromFile("src/test/resources/test_input.csv");
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_invalidPath_notOk() {
        fileReaderService.readFromFile("invalid path");
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_nullPath_notOk() {
        fileReaderService.readFromFile(null);
    }
}
