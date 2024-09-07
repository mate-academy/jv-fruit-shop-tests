package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final ReportGeneratorImpl reportGenerator;

    static {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void generate_validInput_ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 92);
        String expected = new StringBuilder("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,92").append(System.lineSeparator()).toString();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
