package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generateReport_validData_Ok() {
        Storage.getFruits().put("apple", 100);
        Storage.getFruits().put("orange", 50);
        Storage.getFruits().put("banana", 25);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "orange,50" + System.lineSeparator()
                + "banana,25" + System.lineSeparator();

        String result = reportGenerator.getReport();
        assertEquals(expected, result);
    }

    @Test
    void generateReport_emptyData_Ok() {
        String result = reportGenerator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, result);
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }
}
