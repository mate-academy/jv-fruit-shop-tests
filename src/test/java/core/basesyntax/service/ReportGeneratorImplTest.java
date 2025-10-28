package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_withFruits_shouldReturnCorrectFormat() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 30);
        storage.put("apple", 15);

        String report = reportGenerator.getReport(storage);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,15";

        assertEquals(3, report.lines().count());
        assertEquals(true, report.contains("fruit,quantity"));
        assertEquals(true, report.contains("banana,30"));
        assertEquals(true, report.contains("apple,15"));
    }

    @Test
    void getReport_withEmptyStorage_shouldReturnOnlyHeader() {
        Map<String, Integer> storage = new HashMap<>();

        String report = reportGenerator.getReport(storage);

        assertEquals("fruit,quantity", report);
    }
}
