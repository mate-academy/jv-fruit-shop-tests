package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_twoFruits_ok() {
        Storage.getFruits().put("banana", 20);
        Storage.getFruits().put("apple", 15);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }
}
