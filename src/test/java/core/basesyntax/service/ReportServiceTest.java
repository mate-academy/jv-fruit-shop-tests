package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void init() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createValidReport_Ok() {
        Storage.getStorage().put(new Fruit("banana"), 152);
        Storage.getStorage().put(new Fruit("apple"), 90);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReportFromEmptyStorage_Ok() {
        Storage.getStorage().clear();
        String actual = reportService.getReport();
        String expected = "fruit,quantity\n";
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
