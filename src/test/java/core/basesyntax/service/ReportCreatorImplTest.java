package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final ReportCreator reportCreator = new ReportCreatorImpl();
    private static final String HEAD = "fruit,quantity";

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_getReportWithOneFruit_Ok() {
        Storage.storage.put(new Fruit("banana"), 15);
        String expected = HEAD + System.lineSeparator()
                + "banana,15";
        String actual = reportCreator.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_getReportWithMoreThanOneFruit_Ok() {
        Storage.storage.put(new Fruit("banana"), 15);
        Storage.storage.put(new Fruit("apple"), 15);
        Storage.storage.put(new Fruit("cherry"), 15);
        String expected = HEAD + System.lineSeparator()
                + "cherry,15" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,15";
        String actual = reportCreator.createReport();
        Assert.assertEquals(expected, actual);
    }
}
