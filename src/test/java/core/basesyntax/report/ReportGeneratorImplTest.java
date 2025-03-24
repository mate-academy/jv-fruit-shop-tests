package core.basesyntax.report;

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
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "banana,150",
                "apple,200",
                "");

        assertEquals(expectedReport, report);
    }

    @Test
    void generateEmptyReport() {
        String report = reportGenerator.generateReport(storage);
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }
}
