package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.serviceimpl.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void constructorWithNullStorage_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> reportGenerator = new ReportGeneratorImpl(null));
        Assertions.assertTrue(ex.getMessage().contains("Storage can't be null"));
    }

    @Test
    void getReportSuccess_ok() {
        storage.putQuantity("apple", 20);
        storage.putQuantity("banana", 50);
        String report = reportGenerator.getReport();
        Assertions.assertTrue(report.contains("apple,20"));
        Assertions.assertTrue(report.contains("banana,50"));
    }
}
