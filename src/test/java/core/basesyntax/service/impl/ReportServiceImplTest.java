package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.fruitBalance.put("apple", 15);
        Storage.fruitBalance.put("banana", 100);
    }

    @Test
    void createFruitBalanceReport_Ok() {
        StringBuilder expected = new StringBuilder().append("fruit,quantity")
                .append(System.lineSeparator()).append("banana,100")
                .append(System.lineSeparator()).append("apple,15");
        String actual = reportService.createFruitBalanceReport(Storage.fruitBalance);
        assertEquals(expected.toString(), actual);
    }

    @Test
    void createFruitBalanceReport_NegativeQuantity_NotOk() {
        Storage.fruitBalance.put("banana", -50);
        assertThrows(RuntimeException.class,
                () -> reportService.createFruitBalanceReport(Storage.fruitBalance));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitBalance.clear();
    }
}
