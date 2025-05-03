package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    @Test
    void generateReport_validInventory_generatesCorrectReport() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 10);
        inventory.put("cherry", 20);
        inventory.put("banana", 30);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "cherry,20" + System.lineSeparator()
                + "banana,30";
        String actualReport = reportGenerator.generateReport(inventory);
        assertEquals(expectedReport, actualReport,
                "Generated report should match expected format");
    }

    @Test
    void generateReport_emptyInventory_generatesHeaderOnly() {
        Map<String, Integer> inventory = new HashMap<>();
        String expectedReport = "fruit,quantity";
        String actualReport = reportGenerator.generateReport(inventory);
        assertEquals(expectedReport, actualReport,
                "Generated report should contain only the header");
    }

    @Test
    void generateReport_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reportGenerator.generateReport(null),
                "Should throw NullPointerException when inventory is null");
    }
}
