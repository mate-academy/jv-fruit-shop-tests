package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String TITLE = "fruit,quantity\r\n";
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void getReport_correctReport_ok() {
        String expendReport = TITLE
                + "banana,20\r\n"
                + "apple,10";

        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 20);
        storage.put("apple", 10);

        assertEquals(expendReport, reportService.getReport(storage),
                "Invalid data");
    }

}
