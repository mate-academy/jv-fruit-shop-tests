package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @BeforeClass
    public static void beforeClass() {
        Fruit fruitBanana = new Fruit("banana");
        Fruit fruitOrange = new Fruit("orange");
        Fruit fruitMelon = new Fruit("melon");
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
