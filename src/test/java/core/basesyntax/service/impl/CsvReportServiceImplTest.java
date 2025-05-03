package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private static final String REPORT_LEGEND = "fruit,quantity";
    private static final String separator = ",";
    private static final Map<String, Integer> TEST_DATA_MAP = Map.of(
            "banana", 152,
            "apple", 90);
    private ReportService reportService;
    private StringBuilder stringBuilder;

    @Before
    public void setUp() {
        reportService = new CsvReportServiceImpl();
        stringBuilder = new StringBuilder(REPORT_LEGEND);
    }

    @Test
    public void getReport_Ok() {
        TEST_DATA_MAP.forEach((key, value) -> stringBuilder
                .append(System.getProperty("line.separator"))
                .append(key)
                .append(separator)
                .append(value));
        String expected = stringBuilder.toString();
        String actual = reportService.getReport(TEST_DATA_MAP);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
