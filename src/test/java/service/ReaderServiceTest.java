package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ReaderServiceImpl;

public class ReaderServiceTest {
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/empty.csv";
    private static final String PATH_TO_FILE_OK = "src/main/resources/fromfile.csv";
    private static final String PATH_TO_FILE_NOT_OK = "src/main/resources/fromfile32.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void badFileName_notOk() {
        readerService.readFromFile(PATH_TO_FILE_NOT_OK);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_null_notOk() {
        readerService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_Empty_notOk() {
        readerService.readFromFile(PATH_TO_EMPTY_FILE);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for empty file, but it wasn't");
    }

    @Test
    public void readFile_ok() {
        String[] linesFromFile = readerService.readFromFile(PATH_TO_FILE_OK)
                .split(System.lineSeparator());
        assertEquals("First line Expected: " + FIRST_LINE + ", but was: "
                + linesFromFile[0], FIRST_LINE, linesFromFile[0]);
    }
}
