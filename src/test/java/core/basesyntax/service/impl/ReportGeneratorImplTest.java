package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.clear();
        Storage.put("banana", 5);
        Storage.put("apple", 10);
    }

    @Test
    void getReport_generatesReport_ok() {
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity\r\nbanana,5\r\napple,10\r\n";
        assertNotNull(report);
        assertEquals(expected, report);
    }
}
