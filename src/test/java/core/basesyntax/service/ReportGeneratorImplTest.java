package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl generator;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl();
        inventory = new HashMap<>();
    }

    @Test
    void getReport_ok() {
        inventory.put("banana", 50);
        inventory.put("apple", 100);

        String report = generator.getReport(inventory);

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,50"));
        assertTrue(report.contains("apple,100"));
    }

}
