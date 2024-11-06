package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        inventoryData = new HashMap<>();
    }

    @Test
    void returnsCorrectFormat() {
        inventoryData.put("apple", 10);
        inventoryData.put("banana", 10);
        String expectReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,10";
        String actualReport = reportGenerator.generateReport(inventoryData);
        assertEquals(expectReport, actualReport);
    }

    @Test
    void returnsHeaderOnly() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportGenerator.generateReport(inventoryData);
        assertEquals(expectedReport, actualReport);
    }
}
