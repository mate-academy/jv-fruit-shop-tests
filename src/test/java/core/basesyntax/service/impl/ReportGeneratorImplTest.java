package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validStorage_ok() {
        Storage.modifyFruitStorage("apple", 100);
        Storage.modifyFruitStorage("banana", 50);
        String report = reportGenerator.getReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50";
        assertEquals(expectedReport, report);
    }
}
