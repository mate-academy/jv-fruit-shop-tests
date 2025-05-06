package core.basesyntax.converter;

import static org.junit.Assert.assertTrue;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 100);
        Storage.fruitStorage.put("apple", 120);

    }

    @Test
    void getReport_validData_ok() {
        String actual = reportGenerator.getReport();

        assertTrue(actual.contains("fruit,quantity"));
        assertTrue(actual.contains("banana,100"));
        assertTrue(actual.contains("apple,120"));
    }
}
