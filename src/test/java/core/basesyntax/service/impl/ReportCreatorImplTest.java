package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {

    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        Fruit fruitBanana = new Fruit("banana");
        Fruit fruitOrange = new Fruit("orange");
        Fruit fruitMelon = new Fruit("melon");
        Warehouse.getWarehouse().put(fruitBanana, 123);
        Warehouse.getWarehouse().put(fruitOrange, 456);
        Warehouse.getWarehouse().put(fruitMelon, 789);
        expected = "fruit,quantity\n"
                + "banana,123\n"
                + "orange,456\n"
                + "melon,789\n";
    }

    @After
    public void setUp() {
        Warehouse.getWarehouse().clear();
    }

    @Test
    public void createReportForWarehouseWithValues_Ok() {
        String actual = new ReportCreatorImpl().create();
        assertEquals(expected, actual);
    }

    @Test
    public void createReportForEmptyWarehouse() {
        Map<Fruit, Integer> warehouse = Warehouse.getWarehouse();
        expected = "fruit,quantity\n";
        String actual = new ReportCreatorImpl().create();
        assertEquals(expected, actual);
    }
}
