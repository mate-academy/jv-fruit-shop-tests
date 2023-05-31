package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    @Test
    public void createReport_Ok() {
        Storage.storage.put(new Fruit("orange"), 10);
        Storage.storage.put(new Fruit("apple"), 20);
        Storage.storage.put(new Fruit("watermelon"), 30);
        String report = new ReportServiceImpl().getReport();
        Assert.assertTrue(report.startsWith("fruit,quantity")
                && report.contains("orange,10")
                && report.contains("apple,20")
                && report.contains("watermelon,30"));
    }
}
