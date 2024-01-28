package core.basesyntax.service.createreportservice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.createreportservice.CreateReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CreateReportServiceImplTest {
    private final CreateReportService createReportService = new CreateReportServiceImpl();

    @Test
    void createReportTest_ok() {
        Map<String, Integer> reportingValue = new HashMap<>();
        reportingValue.put("orange", 50);
        reportingValue.put("banana", 80);
        reportingValue.put("blueberry", 20);
        String expectedReport = "fruit,quantity\r\norange,50\r\nbanana,80\r\nblueberry,20\r\n";
        String report = createReportService.getReport(reportingValue);
        assertEquals(expectedReport, report);
    }
}
