package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUpFirst() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        Storage.storage.clear();
        String actual = reportService.makeReport(Storage.storage);
        String expected = "fruit,balance" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_validCase_OK() {
        Storage.storage.put(new Fruit("banana"), 18);
        Storage.storage.put(new Fruit("apple"), 14);
        Storage.storage.put(new Fruit("default"), 0);
        String actual = reportService.makeReport(Storage.storage);
        String expected = "fruit,balance" + System.lineSeparator()
                + "banana,18" + System.lineSeparator()
                + "apple,14" + System.lineSeparator()
                + "default,0" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
