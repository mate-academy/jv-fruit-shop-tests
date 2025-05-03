package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.put(new Fruit("banana"), 20);
        Storage.fruits.put(new Fruit("apple"), 2056);
        Storage.fruits.put(new Fruit("cherry"), 2098);
    }

    @Test
    public void getReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "cherry,2098" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,2056";
        String actual = new ReportServiceImpl().getReport();
        assertEquals(expected, actual);
    }
}
