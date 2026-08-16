package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.IReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {

    private final IReportGenerator reportGenerator =
            new ReportGenerator();

    @Test
    void generateReport_emptyStorage_returnsHeaderOnly() {
        Map<String, Integer> storage = new HashMap<>();

        String result = reportGenerator.generateReport(storage);

        assertEquals(
                "fruit,quantity" + System.lineSeparator(),
                result
        );
    }

    @Test
    void generateReport_oneFruit_returnsContent() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 100);

        String result = reportGenerator.generateReport(storage);

        String expected =
                "fruit,quantity"
                        + System.lineSeparator()
                        + "banana,100"
                        + System.lineSeparator();

        assertEquals(expected, result);
    }

    @Test
    void generateReport_multipleFruits_returnsContent() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 152);
        storage.put("apple", 90);

        String result = reportGenerator.generateReport(storage);

        assertTrue(result.contains("fruit,quantity"));
        assertTrue(result.contains("banana,152"));
        assertTrue(result.contains("apple,90"));
    }

    @Test
    void generateReport_zeroQuantity_returnsContent() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 0);

        String result = reportGenerator.generateReport(storage);

        assertTrue(result.contains("banana,0"));
    }
}
