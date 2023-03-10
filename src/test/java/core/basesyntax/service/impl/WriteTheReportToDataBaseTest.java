package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriteTheReportToDataBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteTheReportToDataBaseTest {
    private static final String REPORT_FILE_NAME = "src.test.resources.empty_order.csv";
    private static final String FRUIT = "apple";
    private static final Integer AMOUNT = 10;
    private static List<String> expectedMessage;
    private static WriteTheReportToDataBase writeTheReportToDataBase;
    private static Map<String, Integer> map;

    @BeforeClass
    public static void beforeClass() {
        writeTheReportToDataBase = new WriteTheReportToDataBaseImpl();
        map = new HashMap<>();
        expectedMessage = new ArrayList<>();
    }

    @Before
    public void setUpBeforeTest() {
        expectedMessage.add("fruit,quantity");
        expectedMessage.add("apple,10");
    }

    @After
    public void tearDownAfterTest() {
        map.clear();
        expectedMessage.clear();
    }

    @Test
    public void write_with_valid_file_name_Ok() {
        map.put(FRUIT, AMOUNT);
        writeTheReportToDataBase.write(map, REPORT_FILE_NAME);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read information from the file" + REPORT_FILE_NAME);
        }
        assertEquals(expectedMessage, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_from_empty_map_notOk() {
        writeTheReportToDataBase.write(map, REPORT_FILE_NAME);
        fail("Should throw an exception when the input parameter is empty");
    }

    @Test(expected = RuntimeException.class)
    public void write_empty_file_name_notOk() {
        writeTheReportToDataBase.write(map, "");
        fail("Should throw an exception when the input parameter is empty");
    }

    @Test(expected = RuntimeException.class)
    public void write_with_null_input_parameter_notOk() {
        writeTheReportToDataBase.write(null, REPORT_FILE_NAME);
        fail("Should throw an exception when the input parameter is null");
    }
}
