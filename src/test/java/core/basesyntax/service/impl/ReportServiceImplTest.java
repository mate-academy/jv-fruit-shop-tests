package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportServiceImpl reporter;

    @BeforeEach
    void setUp() {
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
