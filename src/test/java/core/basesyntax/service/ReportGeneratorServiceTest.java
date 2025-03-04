package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceTest {
    private ReportGeneratorService reportGeneratorService;

    @BeforeEach
    void setUp() {
        reportGeneratorService = new ReportGeneratorService();
    }

    @Test
    void generateReport_ValidInventory_ReturnsCorrectReport() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 10);
        inventory.put("banana", 5);
        inventory.put("orange", 20);

        String expectedReport = "fruit,quantity\n"
                + "apple,10\n"
                + "banana,5\n"
                + "orange,20\n";

        String result = reportGeneratorService.generateReport(inventory);

        expectedReport = expectedReport.replace("\r\n", "\n").trim();
        result = result.replace("\r\n", "\n").trim();

        assertEquals(expectedReport, result, "Report does not match the expected output.");
    }

    @Test
    void generateReport_EmptyInventory_ReturnsHeaderOnly() {
        Map<String, Integer> inventory = Map.of();

        String expectedReport = "fruit,quantity\n";

        String result = reportGeneratorService.generateReport(inventory);

        expectedReport = expectedReport.replace("\r\n", "\n").trim();
        result = result.replace("\r\n", "\n").trim();

        assertEquals(expectedReport, result,
                "Report for empty inventory does not match the expected output.");
    }

    @Test
    void generateReport_SingleItemInventory_ReturnsSingleEntry() {
        Map<String, Integer> inventory = Map.of("apple", 10);

        String expectedReport = "fruit,quantity\n"
                + "apple,10\n";

        String result = reportGeneratorService.generateReport(inventory);

        expectedReport = expectedReport.replace("\r\n", "\n").trim();
        result = result.replace("\r\n", "\n").trim();

        assertEquals(expectedReport, result,
                "Report for single item inventory does not match the expected output.");
    }

    @Test
    void generateReport_NullInventory_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> reportGeneratorService.generateReport(null));
    }

    @Test
    void generateReport_CompareTrimmedResults() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 10);
        inventory.put("banana", 5);

        String expectedReport = "fruit,quantity\n"
                + "apple,10\n"
                + "banana,5\n";

        String result = reportGeneratorService.generateReport(inventory);

        expectedReport = expectedReport.replace("\r\n", "\n").trim();
        result = result.replace("\r\n", "\n").trim();

        assertEquals(expectedReport, result, "Trimmed report does not match the expected output.");
    }

}
