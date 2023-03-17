package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriteDataService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteDataServiceImplTest {
    private static WriteDataService writeDataService;
    private static BufferedReader bufferedReader;
    private static final String WRONG_PATH_TO_FILE = "src/main/resources/test/wrongpath.csv";
    private static final String NORMAL_PATH = "src/main/resources/test/test_writedata.csv";
    private static final String EXPECTED_DATA = "write data";
    private static final String EMPTY_DATA = null;

    @BeforeClass
    public static void beforeClass() {
        writeDataService = new WriteDataServiceImpl();
        try {
            bufferedReader = new BufferedReader(new FileReader(NORMAL_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeData_wrongPath_Ok() {
        writeDataService.writeDataToFile(EXPECTED_DATA, WRONG_PATH_TO_FILE);
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
        String dataFromFile;
        try {
            dataFromFile = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Expected: " + EXPECTED_DATA + " but was: "
                + dataFromFile, EXPECTED_DATA, dataFromFile);
    }
}
