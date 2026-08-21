package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void getReport_singleFruit_ok() {
        Storage.getFruits().put("banana", 152);
        String actual = reportGenerator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,152";
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_onlyHeader() {
        String actual = reportGenerator.getReport();
        assertEquals("fruit,quantity", actual);
    }

    @Test
    void getReport_multipleFruits_containsAllEntries() {
        Storage.getFruits().put("banana", 152);
        Storage.getFruits().put("apple", 90);
        String actual = reportGenerator.getReport();
        assertTrue(actual.contains("banana,152"));
        assertTrue(actual.contains("apple,90"));
    }
}
