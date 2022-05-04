package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 152);
        Storage.storage.put(new Fruit("apple"), 90);
    }

    @Test
    public void createReport_ok() {
        String actual = reportService.createReport(Storage.storage);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createEmptyReport_ok() {
        Storage.storage.clear();
        String actual = reportService.createReport(Storage.storage);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
