package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
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
    public void getReport_getReport_Ok() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("banana", 15);

        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,15"
                + System.lineSeparator()
                + "apple,10";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_quantityLessZero_NotOk() {
        Storage.storage.put("banana", -5);
        assertThrows(RuntimeException.class, reportGenerator::getReport);
    }

    @Test
    void getReport_emptyReport_Ok() {
        String actual = "fruit,quantity";
        assertEquals(actual, reportGenerator.getReport());

    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
