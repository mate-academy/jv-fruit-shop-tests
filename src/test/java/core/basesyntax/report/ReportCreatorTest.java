package core.basesyntax.report;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreator();
    }

    @AfterEach
    void clear() {
        fruitStorage.clear();
    }

    @Test
    void reportCreator_createReportWithValidData_ok() {
        fruitStorage.put("apple", 100);
        fruitStorage.put("melon", 100);
        fruitStorage.put("banana", 100);
        String expectedLines = "fruit,quantity"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator()
                + "melon,100"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator();
        String report = reportCreator.createReport();
        assertEquals(expectedLines, report);
    }

    @Test
    void createReport_createReportWithEmptyStorage_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> reportCreator.createReport());
    }
}
