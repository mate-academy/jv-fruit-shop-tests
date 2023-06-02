package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Storage storage;

    @BeforeAll
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_createReport_emptyStorage_ok() {
        Storage.FRUIT_STORAGE.clear();
        String expected = "fruit, quantity" + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_createReport_ok() {
        storage.put("banana", 5);
        storage.put("apple", 100);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @AfterAll
    public static void afterAll() {
        Storage.FRUIT_STORAGE.clear();
    }
}
