package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import org.junit.Before;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CsvReportServiceImplTest extends CsvReportServiceImpl {
    private ReportService csvReportService;
    private String expected;
    private Map<String, Integer> map;

    @Before
    public void setUp() {
        csvReportService = new CsvReportServiceImpl();
        map = new HashMap<>();
        map.put("banana", 40);
        map.put("apple", 20);
        expected = "fruit,quantity" + System.lineSeparator() + "banana,40"
                + System.lineSeparator() + "apple,20";
    }

    @Test
    public void reportService_OkData_Ok() {
        assertEquals(csvReportService.createReport(map), expected);
    }

    @Test (expected = RuntimeException.class)
    public void reportService_NullData_NotOk() {
        csvReportService.createReport(null);
    }
}
