package core.basesyntax.service.impl;

import core.basesyntax.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private Storage storage;
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_validStorage_ok() {
        storage.setFruitQuantity("banana", 20);
        storage.setFruitQuantity("apple", 50);

        String report = reportGenerator.getReport();

        Assertions.assertTrue(report.contains("banana,20"),
                "Report should contain banana with correct quantity.");
        Assertions.assertTrue(report.contains("apple,50"),
                "Report should contain apple with correct quantity.");
    }

    @Test
    void getReport_emptyStorage_ok() {
        String report = reportGenerator.getReport();
        String expectedReport = "fruit,quantity\n";

        Assertions.assertEquals(expectedReport, report,
                "Report for empty storage must contain only header.");
    }
}
