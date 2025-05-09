package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
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

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10",
                "banana,5",
                "orange,20"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport(inventory);

        assertEquals(expectedReport, result, "Report does not match the expected output.");
    }

    @Test
    void generateReport_EmptyInventory_ReturnsHeaderOnly() {
        Map<String, Integer> inventory = Map.of();

        String expectedReport = "fruit,quantity" + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport(inventory);

        assertEquals(expectedReport, result,
                "Report for empty inventory does not match the expected output.");
    }

    @Test
    void generateReport_SingleItemInventory_ReturnsSingleEntry() {
        Map<String, Integer> inventory = Map.of("apple", 10);

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport(inventory);

        assertEquals(expectedReport, result,
                "Report for single item inventory does not match the expected output.");
    }

    @Test
    void generateReport_CompareTrimmedResults() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 10);
        inventory.put("banana", 5);

        String expectedReport = String.join(LINE_SEPARATOR,
                "fruit,quantity",
                "apple,10",
                "banana,5"
        ) + LINE_SEPARATOR;

        String result = reportGeneratorService.generateReport(inventory);

        assertEquals(expectedReport, result, "Trimmed report does not match the expected output.");
    }
}
