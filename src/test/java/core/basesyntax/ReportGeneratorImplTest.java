package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    public static void initialize() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void generateReport_emptyStorage_Ok() {
        String actual = reportGenerator.generateReport();
        assertEquals("fruit,quantity", actual);
    }

    @Test
    public void generateReport_singleFruit_Ok() {
        Storage.fruitStorage.put("banana", 20);
        String actual = reportGenerator.generateReport();
        assertEquals("fruit,quantity"
                + System.lineSeparator() + "banana,20", actual);
    }

    @Test
    public void generateReport_multipleFruits_Ok() {
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("apple", 10);
        String actual = reportGenerator.generateReport();
        assertTrue(actual.startsWith("fruit,quantity"));
        assertTrue(actual.contains("banana,20"));
        assertTrue(actual.contains("apple,10"));
    }
}
