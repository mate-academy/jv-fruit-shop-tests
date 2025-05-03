package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopReportGeneratorTest {
    private FruitShopReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new FruitShopReportGenerator();
        Storage.fruitStorage.clear();
    }

    @Test
    void generateReport_WithEmptyStorage_ReturnsExpectedReport_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void generateReport_WithNonEmptyStorage_ReturnsExpectedReport_Ok() {
        Storage.fruitStorage.put("Apple", 10);
        Storage.fruitStorage.put("Banana", 20);
        Storage.fruitStorage.put("Orange", 15);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "Apple,10" + System.lineSeparator()
                + "Orange,15" + System.lineSeparator()
                + "Banana,20" + System.lineSeparator();
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport);
    }
}
