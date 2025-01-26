package core.basesyntax.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
     void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void reportContentCheck() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("banana", 5);
        Storage.storage.put("orange", 8);
        String report = reportGenerator.getReport(Storage.storage);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "orange,8";

        assertEquals(expectedReport, report);
    }

    @Test
    void emptyStorageCheck() {
        String expected = "fruit,quantity";
        String report = reportGenerator.getReport(Storage.storage);
        assertEquals(expected, report);
    }
}
