package core.basesyntax.report;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.ReportGenerator;
import report.ReportGeneratorImpl;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new HashMap<>();
    }

    @Test
    void generateValidReport() {
        storage.put("banana", 150);
        storage.put("apple", 200);

        String report = reportGenerator.generateReport(storage);
        String[] lines = report.split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertEquals("fruit,quantity", lines[0]);
        assertTrue(report.contains("apple,200"));
        assertTrue(report.contains("banana,150"));
    }

    @Test
    void generateEmptyReport() {
        String report = reportGenerator.generateReport(storage);
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }
}
