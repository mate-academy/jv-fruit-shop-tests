package core.basesyntax.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.db.Storage;
import core.basesyntax.store.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clearStorage();
    }

    @Test
    public void getReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_singleFruit_ok() {
        Storage.modifyFruitStorage("apple", 50);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_multipleFruits_ok() {
        Storage.modifyFruitStorage("banana", 30);
        Storage.modifyFruitStorage("apple", 50);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
