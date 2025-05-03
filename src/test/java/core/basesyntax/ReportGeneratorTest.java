package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.generator.ReportGenerator;
import core.basesyntax.generator.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();

        Storage.fruits.clear();
    }

    @Test
    void generateReport_validReport_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 20);
        String expected = "banana,10\napple,20\n";
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void generateReport_emptyStorage_notOk() {
        assertThrows(RuntimeException.class, () -> reportGenerator.getReport(),
                "Generating a report with an empty storage should throw a RuntimeException.");
    }
}
