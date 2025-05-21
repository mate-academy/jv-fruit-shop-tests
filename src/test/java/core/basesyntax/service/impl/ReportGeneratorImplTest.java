package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @Test
    public void getReport_Ok() {
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
    void quantityLessZero_NotOk() {
        Storage.storage.put("testFruit", -5);
        assertThrows(RuntimeException.class, reportGenerator::getReport);
    }

    @Test
    void getReport_empty() {
        String actual = "fruit,quantity";
        Storage.storage.clear();
        assertEquals(actual, reportGenerator.getReport());

    }

    @AfterEach
    void returnOriginalData() {

        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);
    }
}
