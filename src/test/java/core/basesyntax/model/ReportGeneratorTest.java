package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;
    private static String report;

    @BeforeEach
    void setUp() {
        Map<String, Integer> storage = new LinkedHashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);
        storage.put("orange", 8);
        reportGenerator = new ReportGeneratorImpl();
        report = reportGenerator.getReport(storage);
    }

    @Test
    void reportOk() {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "orange,8";

        assertEquals(expectedReport, report);
    }
}
