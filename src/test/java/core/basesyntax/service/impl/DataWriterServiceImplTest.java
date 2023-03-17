package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.DataWriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataWriterServiceImplTest {
    private static final String VALID_WRITE_DATA_FILE_PATH =
            "src/test/resources/valid_write_data_file.csv";
    private static final String INVALID_WRITE_DATA_FILE_PATH =
            "Z/valid_write_data_file.csv";
    private static final Map<String, Integer> EMPTY_FRUITS = new HashMap<>();
    private static final String VALID_DATA_STRING = "fruit,quantity" + System.lineSeparator()
            + "banana,172" + System.lineSeparator()
            + "apple,190";

    private static Map<String, Integer> fruits;

    static {
        fruits = new HashMap<>();
        fruits.put("banana", 172);
        fruits.put("apple", 190);
    }

    private DataWriterService dataWriterService;

    @Before
    public void setUp() {
        dataWriterService = new DataWriterServiceImpl();
    }

    @Test
    public void writeData_validWrite_Ok() throws IOException {
        dataWriterService.writeData(fruits, VALID_WRITE_DATA_FILE_PATH);

        BufferedReader bufferedReader =
                new BufferedReader(new FileReader(VALID_WRITE_DATA_FILE_PATH));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line).append(System.lineSeparator());
        }
        bufferedReader.close();

        String expected = VALID_DATA_STRING;
        String actual = builder.toString().trim();

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_nullData_notOk() {
        dataWriterService.writeData(null, VALID_WRITE_DATA_FILE_PATH);
        fail("Should throw RuntimeException when we try to write null data to file");
    }

    @Test(expected = RuntimeException.class)
    public void writeData_emptyData_notOk() {
        dataWriterService.writeData(EMPTY_FRUITS, VALID_WRITE_DATA_FILE_PATH);
        fail("Should throw RuntimeException when we try to write empty data to file");
    }

    @Test(expected = RuntimeException.class)
    public void writeData_invalidFilePath_notOk() {
        dataWriterService.writeData(fruits, INVALID_WRITE_DATA_FILE_PATH);
        fail("Should throw RuntimeException when we try to write data to invalid file");
    }
}
