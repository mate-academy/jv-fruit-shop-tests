package core.basesyntax;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private final ReportGeneratorImpl generator = new ReportGeneratorImpl();

    @Test
    void generateReport_validData_ok() {
        Map<String, Integer> reportData = Map.of(
                "apple", 100,
                "banana", 50
        );
        List<String> report = generator.generateReport(reportData);
        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertThat(report.toString(), containsString("apple,100"));
        assertThat(report.toString(), containsString("banana,50"));
    }

    @Test
    void generateReport_emptyData_ok() {
        Map<String, Integer> reportData = Map.of();
        List<String> report = generator.generateReport(reportData);
        assertEquals(1, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }
}
