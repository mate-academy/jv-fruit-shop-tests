package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void getReport_validStorage_ok() {
        Storage.fruitModels.put("apple", 50);
        Storage.fruitModels.put("banana", 20);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String report = reportGenerator.getReport();

        Assertions.assertNotNull(report, "Report cant be null");
        Assertions.assertTrue(report.contains("fruit,quantity"),
                "Report should contain header");
        Assertions.assertTrue(report.contains("apple,50"),
                "Report should contain apple data");
        Assertions.assertTrue(report.contains("banana,20"),
                "Report should contain banana data");
    }

    @Test
    void getReport_emptyStorage_ok() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String report = reportGenerator.getReport();

        Assertions.assertNotNull(report, "Report cant be null");
        Assertions.assertEquals("fruit,quantity", report.trim(),
                "Report should contain only header");
    }
}
