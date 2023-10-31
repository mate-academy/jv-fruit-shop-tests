package service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import db.FruitShopStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportService;

class ReportServiceImplTest {
    private ReportService reportService;
    private Map<String, Integer> fruitShop;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_Ok() {
        fruitShop = FruitShopStorage.fruitShop;
        fruitShop.put("apple", 15);
        fruitShop.put("banana", 26);
        List<String> report = reportService.createReport();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(fruitShop.entrySet());
        for (int i = 1; i < report.size(); i++) {
            String expectedLine = entryList.get(i - 1).getKey()
                    + "," + entryList.get(i - 1).getValue();
            assertTrue(report.contains(expectedLine));
        }
    }
}
