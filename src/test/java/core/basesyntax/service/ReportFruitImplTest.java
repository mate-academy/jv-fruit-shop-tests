package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportFruitImplTest {
    private static ReportFruit reportFruit;

    @BeforeClass
    public static void beforeClass() {
        reportFruit = new ReportFruitImpl();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
    }

    @Test
    public void fruitReport_getReport_OK() {
        Storage.data.put(new Fruit("apple"), 13);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,13" + System.lineSeparator();
        String actual = reportFruit.getReport();
        Assert.assertEquals(expected, actual);
    }
}
