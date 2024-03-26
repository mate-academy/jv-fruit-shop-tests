package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator = new ReportGenerator();

    @Test
    void generateReport_validData_Ok() {
        String actual = reportGenerator.generateReport(Storage.fruits);
        assertEquals(String.class, actual.getClass());
    }
}
