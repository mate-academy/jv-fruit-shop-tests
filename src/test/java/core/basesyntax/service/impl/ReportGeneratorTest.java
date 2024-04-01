package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static final String EXPECTED_VALID = "fruit,quantity\r\nbanana,20\r\n";
    private ReportGenerator reportGenerator = new ReportGenerator();

    @Test
    void generateReport_emptyData_ok() {
        String actual = reportGenerator.generateReport(Storage.fruits);
        assertEquals(String.class, actual.getClass());
    }

    @Test
    void generateReport_validData_ok() {
        Storage.fruits.put("banana", 20);
        assertEquals(reportGenerator.generateReport(Storage.fruits), EXPECTED_VALID);
    }
}
