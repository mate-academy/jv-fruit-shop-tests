package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearUp() {
        Storage.getFruits().clear();
    }

    @Test
    void getReport_nonEpmtyStorage_ok() {
        Storage.getFruits().put("banana", 100);
        Storage.getFruits().put("apple", 50);
        String report = reportGenerator.getReport();
        Assertions.assertTrue(report.contains("banana,100"));
        Assertions.assertTrue(report.contains("apple,50"));
    }

    @Test
    void getReport_emptyStorage_ok() {
        String report = reportGenerator.getReport();
        Assertions.assertTrue(report.contains("fruit,quantity"),
                "Report should contain header even if storage is empty");
    }
}
