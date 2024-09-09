package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        reportGenerator = new ReportGeneratorImpl(fruitStorage);
    }

    @Test
    void createReport_validData_ok() {
        fruitStorage.put("banana", 152);
        fruitStorage.put("apple", 90);
        String expectedReport = "fruit,quantity\nbanana,152\napple,90\n";
        String actualReport = reportGenerator.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
