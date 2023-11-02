package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private Storage storage = new Storage();

    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    @Test
    void generateReport_ok() {
        Storage.addFruits("apple", 10);
        Storage.addFruits("banana", 10);

        ReportGeneratorService report = new ReportGeneratorServiceImpl();
        String actualReport = report.generateReport();
        String validReport = "fruit,quantity\nbanana,10\napple,10\n";
        Assertions.assertEquals(actualReport, validReport);

    }

    @Test
    void generateReport_notOk() {
        Storage.addFruits("apple", 10);
        Storage.addFruits("banana", 10);

        ReportGeneratorService report = new ReportGeneratorServiceImpl();
        String actualReport = report.generateReport();
        String invalidReport = "fruit,quantity\nbanana,12\napple,10\n";
        Assertions.assertNotEquals(actualReport, invalidReport);
    }
}
