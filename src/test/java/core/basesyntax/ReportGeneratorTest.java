package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.generator.ReportGenerator;
import core.basesyntax.generator.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
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
    void reportGenerator_validReport_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 20);
        String expected = "banana,10\napple,20\n";
        Assertions.assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void reportGenerator_EmptyStorage_notOk() {
        String expected = "No records to report";
        Assertions.assertEquals(expected, reportGenerator.getReport());
    }
}
