package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void makeReport_validData_ok() {
        Map<String, Integer> storage = new HashMap<>();

        storage.put("apple", 20);
        storage.put("banana", 30);

        String result = reportGenerator.makeReport(storage);

        assertTrue(result.startsWith("fruit,quantity"));
        assertTrue(result.contains("apple,20"));
        assertTrue(result.contains("banana,30"));
    }

}
