package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {

    private FruitService fruitService;

    @BeforeEach
    public void setUp() {
        fruitService = new FruitServiceImpl();
        Storage.fruits.clear();
    }

    @Test
    public void getReportEmptyStorageShouldReturnHeaderOnly() {
        String report = fruitService.getReport();
        Assertions.assertEquals("fruit,quantity", report);
    }

    @Test
    public void getReportNonEmptyStorageShouldReturnReport() {
        Fruit apple = new Fruit("apple");
        Fruit orange = new Fruit("orange");
        Storage.fruits.put(apple, 10);
        Storage.fruits.put(orange, 20);
        String expectedReport = "fruit,quantity\napple,10\norange,20";
        String actualReport = fruitService.getReport();
        Assertions.assertEquals(expectedReport,
                actualReport, "The report contains unexpected values or format.");
    }

    @Test
    public void getReportMultipleSameFruitsShouldSumQuantities() {
        Storage.fruits.put(new Fruit("orange"), 20);
        Storage.fruits.put(new Fruit("banana"), 15);
        String report = fruitService.getReport();
        Assertions.assertTrue(report.contains("orange,20"));
        Assertions.assertTrue(report.contains("banana,15"));
    }
}
