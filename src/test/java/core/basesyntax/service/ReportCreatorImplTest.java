package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final ReportCreator REPORT_CREATOR = new ReportCreatorImpl();
    private static final String HEAD = "fruit,quantity";

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void getReport_Ok() {
        Storage.storage.put(new Fruit("banana"), 15);
        String expected = HEAD + System.lineSeparator()
                + "banana,15";
        String actual = REPORT_CREATOR.createReport();
        Assert.assertEquals(expected, actual);
    }
}
