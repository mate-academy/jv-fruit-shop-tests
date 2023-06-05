package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl("fruit,quantity", ":");
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    void generateReport_noFruits_ok() {
        List<String> expected = List.of("fruit,quantity");
        List<String> result = reportService.generateReport();
        assertEquals(expected, result);
    }

    @Test
    void generateReport_singleFruit_ok() {
        Storage.fruitMap.put("apple", 10);
        List<String> expected = List.of("fruit,quantity", "apple:10");
        List<String> result = reportService.generateReport();
        assertEquals(expected, result);
    }

    @Test
    void generateReport_multipleFruits_ok() {
        Storage.fruitMap.put("apple", 10);
        Storage.fruitMap.put("banana", 20);
        Storage.fruitMap.put("orange", 15);
        List<String> expected = List.of("fruit,quantity", "apple:10", "banana:20", "orange:15");
        List<String> result = reportService.generateReport();
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
}
