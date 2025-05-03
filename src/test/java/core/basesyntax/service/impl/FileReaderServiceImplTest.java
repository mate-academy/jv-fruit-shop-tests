package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.nio.file.InvalidPathException;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_nullPath_notOk() {
        fileReaderService.readFile(null);
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_wrongPath_notOk() {
        fileReaderService.readFile("wrong path");
    }

    @Test
    public void readFromFile_validPath_ok() {
        String expected = "text for test ";
        String actual = fileReaderService
                .readFile("src/test/resources/inputTest.csv");
        assertEquals(expected, actual);
    }
}
