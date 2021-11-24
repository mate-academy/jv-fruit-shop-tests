package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private final ReportService report = new ReportServiceImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void report_ValidDate_OK() {
        Storage.fruits.put(new Fruit("banana"), 20);
        String expected = "fruit,quantity\n" + "banana,20";
        Assert.assertEquals(expected, report.createReport());
    }

}
