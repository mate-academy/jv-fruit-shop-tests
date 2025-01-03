package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.getFruits().clear();
        Storage.getFruits().put("apple", 100);
        Storage.getFruits().put("orange", 50);
        Storage.getFruits().put("banana", 25);
    }

    @Test
    void validData_Ok() {
        String result = reportGenerator.getReport();
        assertEquals("fruit,quantity\napple,100\norange,50\nbanana,25\n", result);
    }
}
