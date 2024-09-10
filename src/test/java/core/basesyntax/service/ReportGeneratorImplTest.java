package core.basesyntax.service;

import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void report_ValidData_Ok() {
        Map<String, Integer> validData = new TreeMap<>(Map.of(
                "apple", 90,
                "banana", 152));
        String expectedContent = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,90",
                "banana,152");
        String actualContent = reportGenerator.getReport(validData);
        Assertions.assertEquals(expectedContent, actualContent);
    }
}
