package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String DEFAULT_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,35";
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        Storage.storage.put(TEST_FRUIT, 35);
    }

    @Test
    public void create_Report_OK() {
        String actual = reportService.getReport();
        Assert.assertEquals(DEFAULT_REPORT, actual);
    }

    @Test
    public void create_Report_Empty_Storage_OK() {
        String expected = "fruit,quantity";
        Storage.storage.clear();
        String actual = reportService.getReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
