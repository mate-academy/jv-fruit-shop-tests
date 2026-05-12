package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.getInstance().getFruitStorage().clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReportWithStorageDataReturnsCsvReport() {
        Storage.getInstance().getFruitStorage().put("banana", 20);
        Storage.getInstance().getFruitStorage().put("apple", 10);

        String actual = reportGenerator.getReport();

        assertTrue(actual.startsWith("fruit,quantity\n"));
        assertTrue(actual.contains("banana,20\n"));
        assertTrue(actual.contains("apple,10\n"));
    }

    @Test
    void getReportWithEmptyStorageReturnsOnlyHeader() {
        String actual = reportGenerator.getReport();

        assertTrue(actual.equals("fruit,quantity\n"));
    }
}
