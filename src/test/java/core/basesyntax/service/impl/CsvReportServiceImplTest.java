package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class CsvReportServiceImplTest extends CsvReportServiceImpl {
    ReportService csvReportService;
    String expected;
    Map<String, Integer> map;

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
    public void reportService_OKData_OK() {
        assertEquals(csvReportService.createReport(map), expected);
    }

    @Test (expected = RuntimeException.class)
    public void reportService_NullData_NotOK() {
        csvReportService.createReport(null);
    }
}
