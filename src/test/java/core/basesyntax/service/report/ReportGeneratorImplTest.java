package core.basesyntax.service.report;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generate_emptyMap_shouldReturnEmptyList() {
        Map<String, Integer> fruitQuantity = new HashMap<>();
        List<String> report = reportGenerator.generate(fruitQuantity);
        assertEquals(1, report.size());
        assertEquals(HEADER, report.get(0));
    }

    @Test
    void generate_singleEntry_shouldReturnListWithOneEntry() {
        Map<String, Integer> fruitQuantity = new HashMap<>();
        fruitQuantity.put(APPLE, 10);
        List<String> report = reportGenerator.generate(fruitQuantity);
        assertEquals(2, report.size());
        assertEquals(HEADER, report.get(0));
        assertEquals("Apple,10", report.get(1));
    }

    @Test
    void generate_multipleEntries_shouldReturnListWithMultipleEntries() {
        Map<String, Integer> fruitQuantity = new HashMap<>();
        fruitQuantity.put(APPLE, 10);
        fruitQuantity.put("Orange", 20);
        fruitQuantity.put("Banana", 15);
        List<String> report = reportGenerator.generate(fruitQuantity);
        assertEquals(4, report.size());
        assertEquals(HEADER, report.get(0));
        assertEquals("Apple,10", report.get(1));
        assertEquals("Orange,20", report.get(2));
        assertEquals("Banana,15", report.get(3));
    }
}
