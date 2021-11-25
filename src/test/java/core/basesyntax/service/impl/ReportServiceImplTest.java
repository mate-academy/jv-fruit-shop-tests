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
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,20";
        Assert.assertEquals(expected, report.createReport());
    }

    @Test
    public void report_DateWithManyFruits_OK() {
        Storage.fruits.put(new Fruit("banana"), 20);
        Storage.fruits.put(new Fruit("apple"), 5);
        Storage.fruits.put(new Fruit("pineapple"), 45);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,5"
                + System.lineSeparator() + "pineapple,45";
        Assert.assertEquals(expected, report.createReport());
    }
}
