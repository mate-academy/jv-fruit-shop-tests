package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ScvReportServiceImplTest {
    private static final ReportService reportService = new ScvReportServiceImpl();

    @Test
    public void getReport_correctConvert_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,68" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();

        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("banana", 68);
        dataMap.put("apple", 90);
        String actualReport = reportService.getReport(dataMap);
        assertEquals("Test fail! Expected report: " + System.lineSeparator()
                + expectedReport + ", actual report: " + System.lineSeparator()
                + actualReport, expectedReport, actualReport);
    }
}
