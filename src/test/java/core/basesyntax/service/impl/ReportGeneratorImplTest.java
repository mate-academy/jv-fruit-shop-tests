package core.basesyntax.service.impl;

import core.basesyntax.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void getReport_validStorage_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 20);
        storage.setFruitQuantity("apple", 50);
        
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl(storage);
        String report = reportGenerator.getReport();

        Assertions.assertTrue(report.contains("banana,20"),
                "Report should contain banana with correct quantity.");
        Assertions.assertTrue(report.contains("apple,50"),
                "Report should contain apple with correct quantity.");
    }
}
