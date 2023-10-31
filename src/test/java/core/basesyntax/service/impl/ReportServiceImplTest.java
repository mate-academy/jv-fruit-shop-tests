package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private ReportService reportService;
    private List<String> expectedList;
    private Map<String, Integer> testStorage;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        expectedList = new ArrayList<>();
        testStorage = new HashMap<>();
    }

    @Test
    void createReport_createsReportWithNormalData_Ok() {
        testStorage.put("banana", 100);
        testStorage.put("apple", 20);
        expectedList.add(HEADER);
        expectedList.add("banana,100");
        expectedList.add("apple,20");

        assertEquals(expectedList, reportService.createReport(testStorage));
    }

    @Test
    void createReport_createsReportWithEmptyStorage_Ok() {
        expectedList.add(HEADER);
        assertEquals(expectedList, reportService.createReport(testStorage));
    }
}
