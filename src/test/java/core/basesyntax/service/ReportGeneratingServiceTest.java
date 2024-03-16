package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.ReportGeneratingServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratingServiceTest {
    private static ReportGeneratingService reportGeneratingService;

    @BeforeAll
    static void beforeAll() {
        reportGeneratingService = new ReportGeneratingServiceImpl();
    }

    @Test
    void reportGeneratingService_NullValue_NotOk() {
        Storage.fruitDB.put("fruit", null);
        assertThrows(RuntimeException.class,
                () -> reportGeneratingService.generateReportViaStorage(),
                "The value must not be NULL");
    }

    @Test
    void reportGeneratingService_ContainsFruit_Ok() {
        String apple = "apple";
        int quantity = 10;
        Storage.fruitDB.put(apple, quantity);

        String reportStr = reportGeneratingService.generateReportViaStorage();
        boolean actual = reportStr.contains(apple);

        assertTrue(actual, "Report must contains Fruit - Apple");
    }

    @Test
    void reportGeneratingService_ContainsQuantity_Ok() {
        String apple = "apple";
        int quantity = 10;
        Storage.fruitDB.put(apple, quantity);

        String reportStr = reportGeneratingService.generateReportViaStorage();
        boolean actual = reportStr.contains(String.valueOf(quantity));

        assertTrue(actual, "Report must contains Quantity - 10");
    }

    @AfterEach
    void tearDown() {
        Storage.fruitDB.clear();
    }
}
