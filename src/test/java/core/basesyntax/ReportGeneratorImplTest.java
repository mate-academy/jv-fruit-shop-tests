package core.basesyntax;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportGeneratorImplTest {

    private final ReportGeneratorImpl generator = new ReportGeneratorImpl();

    @Test
    void generateReport_validData() {
        Map<String, Integer> reportData = Map.of(
                "apple", 100,
                "banana", 50
        );
        List<String> report = generator.generateReport(reportData);
        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertEquals("apple,100", report.get(1));
        assertEquals("banana,50", report.get(2));
    }

    @Test
    void generateReport_emptyData() {
        Map<String, Integer> reportData = Map.of();
        List<String> report = generator.generateReport(reportData);
        assertEquals(1, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }
}
