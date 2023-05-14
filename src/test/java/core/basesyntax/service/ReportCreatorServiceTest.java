package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private static final String FIRST_EXPECTED_FRUIT_DATA = "banana,20";
    private static final String SECOND_EXPECTED_FRUIT_DATA = "apple,100";
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
    public void createReport_nullData_notOk() {
        reportCreatorService.createReport(null);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_emptyData_notOk() {
        reportCreatorService.createReport(Collections.EMPTY_MAP);
    }

    @Test
    public void createReport_NoCsvHeader_notOk() {
        String actual = reportCreatorService.createReport(mockData);
        assertTrue(actual.toLowerCase().contains(FILE_HEADER));
    }

    @Test
    public void createReport_ok() {
        String actual = reportCreatorService.createReport(mockData);
        String expected = FILE_HEADER + System.lineSeparator()
                + FIRST_EXPECTED_FRUIT_DATA + System.lineSeparator()
                + SECOND_EXPECTED_FRUIT_DATA + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
