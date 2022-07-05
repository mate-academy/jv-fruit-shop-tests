package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private static Fruit fruitBanana = new Fruit("banana");
    private static Fruit fruitOrange = new Fruit("orange");
    private static Fruit fruitMelon = new Fruit("melon");

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
        fruitBanana = new Fruit("banana");
        fruitOrange = new Fruit("orange");
        fruitMelon = new Fruit("melon");
        Warehouse.getWarehouse().put(fruitBanana, 123);
        Warehouse.getWarehouse().put(fruitOrange, 456);
        Warehouse.getWarehouse().put(fruitMelon, 789);
    }

    @After
    public void setUp() {
        Warehouse.getWarehouse().clear();
    }

    @Test
    public void create_warehouseWithValues_Ok() {
        String expected = "fruit,quantity\n"
                + "banana,123\n"
                + "orange,456\n"
                + "melon,789\n";
        String actual = reportCreator.create();
        assertEquals(expected, actual);
    }

    @Test
    public void create_emptyWarehouse_Ok() {
        String expected = "fruit,quantity\n";
        String actual = reportCreator.create();
        assertEquals(expected, actual);
    }
}
