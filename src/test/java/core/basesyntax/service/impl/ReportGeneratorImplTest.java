package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getReport_validData_Ok() {
        Storage.putFruit("banana", 152);
        Storage.putFruit("apple", 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
