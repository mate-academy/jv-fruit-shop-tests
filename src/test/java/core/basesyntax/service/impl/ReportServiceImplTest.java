package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void getReport_fromStorage_oK() {
        Storage.storage.put("banana", 65);
        Storage.storage.put("apple", 45);
        String actual = reportService.getReport();
        String expected = "banana,65" + System.lineSeparator()
                + "apple,45" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
