package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();

        Storage.getFruits().put("banana", 5);
        Storage.getFruits().put("apple", 10);
    }

    @Test
    void generate_validData_ok() {
        String report = reportGenerator.generate(Storage.getFruits());

        assertTrue(report.contains("banana"));
        assertTrue(report.contains("apple"));
    }
}
