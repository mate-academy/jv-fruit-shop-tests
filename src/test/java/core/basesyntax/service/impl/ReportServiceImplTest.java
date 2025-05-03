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
    public static void beforeAll() {
        reporter = new ReportServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    public void createReport_EmptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator() + System.lineSeparator();
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_WithOneItem_ok() {
        Storage.FRUITS.put("apple", 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator();
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_StorageWithMultipleItems_ok() {
        Storage.FRUITS.put("apple", 10);
        Storage.FRUITS.put("orange", 20);
        Storage.FRUITS.put("banana", 30);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "orange,20"
                + System.lineSeparator()
                + "banana,30"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator();
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }
}
