package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceCsvImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceCsvImpl();
    }

    @Before
    public void beforeEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void formReport_ok() {
        Fruit orange = new Fruit("orange");
        int quantity = 10;
        Storage.storage.put(orange, quantity);
        String actual = reportService.formReport();
        String expected = "fruit,quantity\n"
                + "orange,10";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formReport_null_notOk() {
        String actual = reportService.formReport();
        String expected = "fruit,quantity";
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
