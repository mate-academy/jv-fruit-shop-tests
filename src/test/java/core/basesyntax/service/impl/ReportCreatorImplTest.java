package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class ReportCreatorImplTest {

    @Test
    public void createReport_Ok() {
        Fruit fruitBanana = new Fruit("banana");
        Fruit fruitOrange = new Fruit("orange");
        Fruit fruitMelon = new Fruit("melon");
        Warehouse.getWarehouse().put(fruitBanana, 123);
        Warehouse.getWarehouse().put(fruitOrange, 456);
        Warehouse.getWarehouse().put(fruitMelon, 789);

        String expected = "fruit,quantity\n"
                + "banana,123\n"
                + "orange,456\n"
                + "melon,789\n";
        String actual = new ReportCreatorImpl().create();
        assertEquals(expected, actual);
    }

}
