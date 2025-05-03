package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void setUp() {
        reportService = new ReportService(new FruitDaoImpl());
        Storage.fruits.put(BANANA, 152);
        Storage.fruits.put(APPLE, 90);
    }

    @Test
    void generateReport_nullFruit_notOk() {
        Storage.fruits.clear();
        assertThrows(RuntimeException.class, () -> reportService.generateReport());
    }

    @Test
    void generateReport_FruitValues_ok() {
        String expected = "type,fruit" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        assertEquals(expected, reportService.generateReport());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
