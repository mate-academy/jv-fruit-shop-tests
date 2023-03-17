package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriteDataService;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteDataServiceImplTest {
    private static WriteDataService writeDataService;
    private static ReaderService readerService;
    private static final String WRONG_PATH_TO_FILE = "src/main/resources/test/wrongpath.csv";
    private static final String NORMAL_PATH = "src/main/resources/test/test_writedata.csv";
    private static final String EXPECTED_DATA = "write data";
    private static final String EMPTY_DATA = null;

    @BeforeClass
    public static void beforeClass() {
        writeDataService = new WriteDataServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = AssertionError.class)
    public void writeData_wrongPath_notOk() {
        writeDataService.writeDataToFile(EXPECTED_DATA, WRONG_PATH_TO_FILE);
        fail("Expected " + AssertionError.class.getName() + " to be thrown for wrong path "
                + WRONG_PATH_TO_FILE + " but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void writeData_nullData_notOk() {
        writeDataService.writeDataToFile(EMPTY_DATA, NORMAL_PATH);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null data but "
                + "it's wasn't");
    }

    @Test
    public void writeData_ok() {
        writeDataService.writeDataToFile(EXPECTED_DATA, NORMAL_PATH);
        String dataFromFile = readerService.readData(NORMAL_PATH);
        assertEquals("Expected: " + EXPECTED_DATA + " but was: "
                + dataFromFile, EXPECTED_DATA, dataFromFile);
    }
}
