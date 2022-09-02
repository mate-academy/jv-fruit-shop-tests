package core.basesyntax.storage;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class StorageTest {
    @Test
    public void getReport_Ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        String expected = "fruit,quantity\n"
                + "banana,10\n";
        ReportServiceImpl reportService = new ReportServiceImpl();
        String actual = reportService.getReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
