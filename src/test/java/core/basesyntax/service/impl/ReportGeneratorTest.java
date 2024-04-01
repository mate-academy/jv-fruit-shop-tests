package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator = new ReportGenerator();

    @Test
    void generateReport_emptyData_ok() {
        String actual = reportGenerator.generateReport(Storage.fruits);
        assertEquals(String.class, actual.getClass());
    }

    @Test
    void generateReport_validData_ok() {
        Storage.fruits.put("banana", 20);
        String expected = "fruit,quantity\r\n"
                + "banana,20\r\n";
        assertEquals(expected, reportGenerator.generateReport(Storage.fruits));
    }
}
