package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceImplTest {
    private static FruitReportService report;

    @BeforeClass
    public static void beforeClass() {
        report = new FruitReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
    }

    @Test
    public void report_getReport_ok() {
        Storage.data.put(new Fruit("banana"), 10);
        StringBuilder builder = new StringBuilder();
        String expected ="fruit,quantity\nbanana,10\n";
        String actual = report.getReport();
        Assert.assertEquals(expected, actual);
    }
}
