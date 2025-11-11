package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    @BeforeEach
    void clear() {
        Storage.clear();
    }

    @Test
    void reportGenerator_ok() {
        Storage.setQuantity("banana", 20);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        assertEquals(expected, new ReportGeneratorImpl().getReport());
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String expected = "fruit,quantity" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_returnsFormattedReport() {
        Storage.setQuantity("banana", 10);
        Storage.setQuantity("apple", 5);

        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        String report = generator.getReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,10"));
        assertTrue(report.contains("apple,5"));
    }
}
