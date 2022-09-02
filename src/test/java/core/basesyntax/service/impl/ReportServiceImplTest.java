package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_EmptyStorage_Ok() {
        Storage.storage.clear();
        assertEquals("fruit,quantity", reportService.getReport());
    }

    @Test
    public void getReport_StorageNotEmpty() {
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 5);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator() + "apple,5";
        assertEquals(expected, reportService.getReport());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
