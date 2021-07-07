package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitReportImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportTest {
    private static FruitReport fruitReport;

    @BeforeClass
    public static void beforeClass() {
        fruitReport = new FruitReportImpl();
    }

    @Test
    public void getReport_fromStorage_Ok() {
        Storage.storage.put(new Fruit("banana"), 20);
        Storage.storage.put(new Fruit("apple"), 100);
        String actual = fruitReport.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
