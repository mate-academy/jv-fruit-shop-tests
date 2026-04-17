package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Map<String, Integer> storage;
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generateReport_nullStorage_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportGenerator.generateReport(null));
    }

    @Test
    void generateReport_emptyStorage_ok() {
        assertThrows(RuntimeException.class,
                () -> reportGenerator.generateReport(storage));
    }

    @Test
    void generateReport_validStorage_ok() {
        storage.put("fruit", 1);
        String actual = reportGenerator.generateReport(storage);
        String expected = "fruit,quantity\nfruit,1\n";
        assertEquals(expected, actual);
    }
}
