package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void getReport_withData_ok() {
        Storage.setFruitQuantity("banana", 50);
        Storage.setFruitQuantity("apple", 30);

        String expectedReport = """
                fruit,quantity
                apple,30
                banana,50
                """;

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
