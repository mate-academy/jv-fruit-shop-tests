package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void shouldGenerateReport_whenStorageEmpty() {
        Map<String, Integer> storage = new HashMap<>();

        ReportGeneratorImpl generator = new ReportGeneratorImpl(storage);

        assertEquals("fruit,quantity\n", generator.getReport());
    }

    @Test
    void shouldGenerateReport_whenStorageHasData() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        ReportGeneratorImpl generator = new ReportGeneratorImpl(storage);

        String report = generator.getReport();

        assertEquals(true, report.contains("apple,10"));
    }
}
