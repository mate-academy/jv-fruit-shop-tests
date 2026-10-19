package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void getReport_validStorage_ok() {
        Storage.fruitModels.put("apple", 50);
        Storage.fruitModels.put("banana", 20);
        String report = reportGenerator.getReport();

        assertNotNull(report, "Report cant be null");
        assertTrue(report.contains("fruit,quantity"),
                "Report should contain header");
        assertTrue(report.contains("apple,50"),
                "Report should contain apple data");
        assertTrue(report.contains("banana,20"),
                "Report should contain banana data");
    }

    @Test
    void getReport_emptyStorage_ok() {
        String report = reportGenerator.getReport();

        assertNotNull(report, "Report cant be null");
        assertEquals("fruit,quantity", report.trim(),
                "Report should contain only header");
    }
}
