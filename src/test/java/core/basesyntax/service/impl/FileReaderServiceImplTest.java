package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static ReaderService fileReaderService;
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_input.csv";
    private static final String WRONG_FILE_PATH = "google/output.xml";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String data;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        data = "b,banana,20" + LINE_SEPARATOR
                + "b,apple,100" + LINE_SEPARATOR
                + "s,banana,100" + LINE_SEPARATOR
                + "p,banana,13" + LINE_SEPARATOR
                + "r,apple,10" + LINE_SEPARATOR
                + "p,apple,20" + LINE_SEPARATOR
                + "p,banana,5" + LINE_SEPARATOR
                + "s,banana,50" + LINE_SEPARATOR;
    }

    @Test
    public void readFromFile_validData_ok() {
        String expected = data;
        String actual = fileReaderService.read(CORRECT_FILE_PATH);
        Assert.assertEquals("The read method should return: " + expected, expected, actual);
    }

    @Test
    public void readFromFile_emptyData_ok() {
        boolean actual = fileReaderService.read(EMPTY_FILE_PATH)
                .isEmpty();
        Assert.assertTrue(actual);
    }

    @Test
    public void readFromFile_correctPath_ok() {
        String expected = data;
        String dataFromFile = fileReaderService.read(CORRECT_FILE_PATH);
        Assert.assertEquals(expected, dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_incorrectPath_notOk() {
        fileReaderService.read(WRONG_FILE_PATH);
    }
}
