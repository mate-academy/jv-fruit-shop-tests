package service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.FruitShopStorage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportService;

class ReportServiceImplTest {
    private ReportService reportService;
    private List<String> report;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        report = reportService.createReport();
    }

    @Test
    void createReport_Ok() {
        Map<String, Integer> fruitShop = FruitShopStorage.fruitShop;

        for (Map.Entry<String, Integer> entry : fruitShop.entrySet()) {
            String expectedLine = entry.getKey() + "," + entry.getValue();
            assertTrue(report.contains(expectedLine));
        }
    }

    @Test
    void createReportNotEmpty_Ok() {
        assertFalse(report.isEmpty());
    }

    @Test
    void createReportHeader_Ok() {
        assertTrue(report.contains("fruit,quantity"));
    }

}
