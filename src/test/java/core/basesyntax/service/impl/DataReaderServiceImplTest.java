package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.DataReaderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataReaderServiceImplTest {
    private static final String VALID_READ_DATA_FILE_PATH =
            "src/test/resources/valid_read_data_file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private static final String NON_EXISTENT_FILE_PATH =
            "src/test/resources/non_existent_file.csv";
    private static final List<String> VALID_DATA_LIST =
            Arrays.asList("b,banana,20", "b,apple,100", "s,banana,100");
    private DataReaderService dataReaderService;

    @Before
    public void setUp() {
        dataReaderService = new DataReaderServiceImpl();
    }

    @Test
    public void readData_validReadData_ok() {
        List<String> actual = dataReaderService.readData(VALID_READ_DATA_FILE_PATH);
        Assert.assertEquals(VALID_DATA_LIST, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_nonExistentFile_notOk() {
        dataReaderService.readData(NON_EXISTENT_FILE_PATH);
        fail("Should throw RuntimeException when we try to read from non-existent file");
    }

    @Test
    public void readData_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = dataReaderService.readData(EMPTY_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }
}
