package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validReport_ok() {
        Storage.getFruits().clear();
        Storage.getFruits().add(new Fruit("banana", 10));
        String report = reportGenerator.getReport();
        assertNotNull(report);
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,10"));
    }

    @Test
    void getReport_reportIsEmpty_ok() {
        Storage.getFruits().clear();
        String report = reportGenerator.getReport();

        assertEquals("fruit,quantity", report);
    }
}
