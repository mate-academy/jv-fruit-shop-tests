package core.basesyntax.shop;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorTest {
    private static ReportCreator reportCreator;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreator();
        fruit = new Fruit("banana");
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Ok() {
        Storage.fruits.put(fruit, 100);
        String expecct = "fruit,quantity\nbanana, 100\n";
        String actual = reportCreator.createReport();
        assertEquals(expecct, actual);
    }
}
