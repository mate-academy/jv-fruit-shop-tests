package core.basesyntax.service;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(new FruitDaoImpl());
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    @Test
    void generateReport_nullFruit_notOk() {
        Storage.fruits.clear();
        Assertions.assertThrows(RuntimeException.class, () -> reportService.generateReport());
    }

    @Test
    void generateReport_FruitValues_ok() {
        String expected = "type,fruit" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        Assertions.assertEquals(expected, reportService.generateReport());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
