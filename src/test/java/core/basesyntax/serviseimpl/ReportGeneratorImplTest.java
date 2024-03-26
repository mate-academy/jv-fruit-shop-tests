package core.basesyntax.serviseimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Map<String, Integer> fruitStore;

    @BeforeEach
    void setUp() {
        fruitStore = new LinkedHashMap<>();
        fruitStore.put("apple", 10);
        fruitStore.put("banana", 20);
        reportGenerator = new ReportGeneratorImpl(fruitStore);
    }

    @Test
    void generateReport() {
        String expected = "fruit,quantity\napple,10\nbanana,20\n";
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
