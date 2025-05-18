package core.basesyntax.service;

import static core.basesyntax.db.Storage.inventory;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private ReportGeneratorService reportGeneratorService;

    @BeforeEach
    void setUp() {
        inventory.clear();
        reportGeneratorService = new ReportGeneratorService();
    }

    @AfterEach
    void tearDown() {
        inventory.clear();
    }

    @Test
    void generateReport_ValidInventory_ReturnsCorrectReport() {
        inventory.put("apple", 10);
        inventory.put("banana", 5);
        inventory.put("orange", 20);

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10",
                "banana,5",
                "orange,20"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport();

        assertEquals(expectedReport, result);
    }

    @Test
    void generateReport_EmptyInventory_ReturnsHeaderOnly() {
        String expectedReport = "fruit,quantity" + LINE_SEPARATOR;
        String result = reportGeneratorService.generateReport();

        assertEquals(expectedReport, result);
    }

    @Test
    void generateReport_SingleItemInventory_ReturnsSingleEntry() {
        inventory.put("apple", 10);

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport();

        assertEquals(expectedReport, result);
    }

    @Test
    void generateReport_CompareTrimmedResults() {
        inventory.put("apple", 10);
        inventory.put("banana", 5);

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10",
                "banana,5"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport();

        assertEquals(expectedReport.trim(), result.trim());
    }
}
