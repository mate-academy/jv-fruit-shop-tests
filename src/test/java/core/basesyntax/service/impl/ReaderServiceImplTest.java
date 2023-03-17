package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String EXPECTED_DATA = "data";
    private static final String EMPTY_PATH = "";
    private static final String BAD_PATH = "src/main/resources/test/wrongpathtoread.csv";
    private static final String PATH_TO_GOOD_FILE = "src/main/resources/test/test_containsdata.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/test/test_empty_file.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_badPath_notOk() {
        readerService.readData(BAD_PATH);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for wrong path "
                + BAD_PATH + " but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullPath_notOk() {
        readerService.readData(EMPTY_PATH);
        readerService.readData(null);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null path '"
                + EMPTY_PATH + "' but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullData_notOk() {
        readerService.readData(PATH_TO_EMPTY_FILE);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null data file "
                + PATH_TO_EMPTY_FILE + " but it's wasn't");

    }

    @Test
    public void readFile_ok() {
        String dataFromFile = readerService.readData(PATH_TO_GOOD_FILE);
        assertEquals("Expected: " + EXPECTED_DATA + " but was: "
                + dataFromFile, EXPECTED_DATA, dataFromFile);
    }
}
