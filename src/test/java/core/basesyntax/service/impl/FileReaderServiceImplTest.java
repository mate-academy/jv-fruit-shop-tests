package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String EMPTY_FILE_PATH = "src/main/resources/EmptyFile.csv";
    private static final String CORRECT_FILE_PATH = "src/main/resources/CorrectFile.csv";
    private static final String WRONG_FILE_PATH = "src/main/resources/File.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_emptyFile_ok() {
        String expected = "";
        String actual = fileReaderService.readFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_wrongPath_notOk() {
        fileReaderService.readFile(WRONG_FILE_PATH);
    }

    @Test
    public void readFile_correctFile_isOk() {
        String expected = "Some correct information";
        String actual = fileReaderService.readFile(CORRECT_FILE_PATH);
        assertEquals(expected, actual);
    }
}
