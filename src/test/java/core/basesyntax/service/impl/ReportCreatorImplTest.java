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
    private static Fruit FRUIT_BANANA = new Fruit("banana");
    private static Fruit FRUIT_ORANGE = new Fruit("orange");
    private static Fruit FRUIT_MELON = new Fruit("melon");

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
        FRUIT_BANANA = new Fruit("banana");
        FRUIT_ORANGE = new Fruit("orange");
        FRUIT_MELON = new Fruit("melon");
        Warehouse.getWarehouse().put(FRUIT_BANANA, 123);
        Warehouse.getWarehouse().put(FRUIT_ORANGE, 456);
        Warehouse.getWarehouse().put(FRUIT_MELON, 789);
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
