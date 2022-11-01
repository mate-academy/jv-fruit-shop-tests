package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    public static final String PATH = "src/test/resources/dataFrom.csv";
    public static final String EMPTY_FILE_PATH = "src/test/resources/EmptyFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        String actual = fileReaderService.readFile(PATH);
        String expected = "Text for ReadFromFileImpl class tests";
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_InvalidPath_NotOk() {
        fileReaderService.readFile("");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_NullPath_NotOk() {
        fileReaderService.readFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_EmptyData_NotOk() {
        fileReaderService.readFile(EMPTY_FILE_PATH);
    }
}
