package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearDown() {
        Storage.getInstance().getFruitStorage().clear();
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
