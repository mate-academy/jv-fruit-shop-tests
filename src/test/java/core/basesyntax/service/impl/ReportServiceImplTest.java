package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reporter;

    @BeforeAll
    static void beforeAll() {
        reporter = new ReportServiceImpl();
    }

    @Test
    void createReport_emptyStorage_ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator();
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_storageWithData_ok() {
        Storage.put("banana", 20);
        Storage.put("apple", 100);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator();
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void clear() {
        Storage.fruitStorage.clear();
    }
}
