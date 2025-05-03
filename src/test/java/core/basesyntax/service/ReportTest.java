package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportTest {
    private ReportService report;

    @Before
    public void setUp() {
        report = new ReportServiceImpl();
        Storage.storage.put(new Fruit("orange"), 25);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void formReport_validData_ok() {
        String expected = "fruit,quantity\napple,100\norange,25\n";
        String actual = report.formReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formReport_emptyStorage_ok() {
        Storage.storage.clear();
        String expected = "fruit,quantity\n";
        String actual = report.formReport();
        Assert.assertEquals(expected, actual);
    }
}
