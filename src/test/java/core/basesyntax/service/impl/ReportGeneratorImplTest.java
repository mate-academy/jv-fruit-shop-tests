package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.clearAll();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_storageWithFruit_returnsFormattedReport() {
        Storage.putFruit("banana", 100);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100";

        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        String expected = "fruit,quantity" + System.lineSeparator();

        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
