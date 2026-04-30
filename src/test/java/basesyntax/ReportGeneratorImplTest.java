package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void getReport_emptyStorage_ok() {
        Map<String, Integer> storage = new HashMap<>();

        ReportGeneratorImpl generator = new ReportGeneratorImpl(storage);

        assertEquals("fruit,quantity\n", generator.getReport());
    }

    @Test
    void getReport_notEmptyStorage_ok() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        ReportGeneratorImpl generator = new ReportGeneratorImpl(storage);

        String report = generator.getReport();

        String expected = "fruit,quantity\napple,10\n";
        assertEquals(expected, generator.getReport());
    }
}
