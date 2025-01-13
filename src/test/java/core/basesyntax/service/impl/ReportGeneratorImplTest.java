package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    private ReportGeneratorService reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clearStorage();
    }

    @Test
    public void getReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_singleFruit_ok() {
        Storage.modifyFruitStorage("apple", 50);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "apple,50";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_multipleFruitsSorted_ok() {
        Storage.modifyFruitStorage("banana", 30);
        Storage.modifyFruitStorage("apple", 50);
        Storage.modifyFruitStorage("orange", 40);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "orange,40";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
