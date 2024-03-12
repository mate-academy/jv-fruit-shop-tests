package service;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generate_validReport_ok() {
        Map<String, Integer> data = Map.of("banana", 10);
        String header = "fruit,quantity";
        String report = "banana,10";
        String actual = reportGenerator.generate(data);
        Assertions.assertTrue(actual.contains(header)
                        && actual.contains(report),
                "Error creating report!");
    }

    @Test
    void generate_emptyReport_ok() {
        Map<String, Integer> data = new HashMap<>();
        String header = "fruit,quantity";
        String actual = reportGenerator.generate(data);
        Assertions.assertTrue(actual.contains(header),
                "Error creating report!");
    }
}
