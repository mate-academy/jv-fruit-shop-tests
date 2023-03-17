package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private static final int EXPECTED_BANANA_QUANTITY = 20;
    private static final int EXPECTED_APPLE_QUANTITY = 100;
    private static ReportCreatorService reportCreatorService;
    private static Map<String, Integer> mockData;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
        mockData = new HashMap<>();
        mockData.put("banana", 20);
        mockData.put("apple", 100);
    }

    @AfterClass
    public static void clearData() {
        mockData.clear();
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullData_NotOk() {
        reportCreatorService.createReport(null);
        fail("An error was expected in case of null data source"
                + " for creating report");
    }

    @Test(expected = RuntimeException.class)
    public void createReport_emptyData_NotOk() {
        reportCreatorService.createReport(Collections.EMPTY_MAP);
        fail("An error was expected in case of empty"
                + " data source for creating report");
    }

    @Test
    public void createReport_NoCsvHeader_NotOk() {
        String actual = reportCreatorService.createReport(mockData);
        assertTrue(actual.toLowerCase().contains(FILE_HEADER));
    }

    @Test
    public void createReport_Ok() {
        String actual = reportCreatorService.createReport(mockData);
        String expected = FILE_HEADER + System.lineSeparator()
                + "banana" + COMMA + EXPECTED_BANANA_QUANTITY + System.lineSeparator()
                + "apple" + COMMA + EXPECTED_APPLE_QUANTITY + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
