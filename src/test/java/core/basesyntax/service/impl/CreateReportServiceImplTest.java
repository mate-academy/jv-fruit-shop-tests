package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.CreateReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final String FIRST_ROW = "fruits,quantity";
    private static final String FRUIT_BANANA = "banana";
    private static final int VALID_QUANTITY = 20;
    private static final String LINE_SEPARATOR = ",";
    private static CreateReportService createReportService;
    private static Map<String, Integer> data;

    @BeforeClass
    public static void beforeClass() {
        createReportService = new CreateReportServiceImpl();
        data = new HashMap<>();
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullData_notOk() {
        createReportService.createReport(null);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null data "
                + "but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void createReport_emptyData_notOk() {
        String report = createReportService.createReport(data);
        String expected = FIRST_ROW + System.lineSeparator();
        assertEquals(expected, report);
    }

    @After
    public void tearDown() {
        data.clear();
    }

    @Test
    public void createReport_ok() {
        StringBuilder expectedReport = new StringBuilder();
        String expected = expectedReport.append(FIRST_ROW).append(System.lineSeparator())
                .append(FRUIT_BANANA)
                .append(LINE_SEPARATOR)
                .append(VALID_QUANTITY)
                .append(System.lineSeparator())
                .toString();

        data.put(FRUIT_BANANA, VALID_QUANTITY);
        String report = createReportService.createReport(data);
        assertEquals("Expected: " + expected + " but was: "
                + report, expected, report);
    }
}
