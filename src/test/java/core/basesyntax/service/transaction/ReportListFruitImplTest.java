package core.basesyntax.service.transaction;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportListFruitImplTest {
    private static final String DELIMITER = ",";
    private ReportListFruit reportListFruit;
    private StringBuilder builder;

    @BeforeEach
    void setUp() {
        reportListFruit = new ReportListFruitImpl();
        builder = new StringBuilder();
    }

    @Test
    void reportListIsWork_ok() {
        Assertions.assertDoesNotThrow(() -> {
            reportListFruit.createReport();
        });
    }

    @Test
    void fruitDbKeyIsExist_ok() {
        Storage.fruitsDB.put("apple", 100);
        Assertions.assertDoesNotThrow(() -> {
            Integer apple = Storage.fruitsDB.get("apple");
            Assertions.assertEquals(apple, 100);
        });
    }
}
