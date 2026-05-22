package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.ReportGenerator;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl(storage);
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    void getReport_withFruits_returnsCorrectReport() {
        storage.getFruits().put("apple", 50);
        storage.getFruits().put("banana", 30);
        ReportGenerator reportGenerator = new ReportGeneratorImpl(storage);
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity\napple,50\nbanana,30\n";
        assertEquals(expected, report);
    }
}
