package core.basesyntax.service.impl.operationhandlersimpls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private SupplyHandler supplyHandler = new SupplyHandler();
    private FruitRecord fruitRecord = new FruitRecord("b", new Fruit("banana"), 10);

    @Before
    public void setUp() throws Exception {
        Fruit banana = new Fruit("banana");
        Fruit orange = new Fruit("orange");
        Storage.storage.put(banana, 10);
        Storage.storage.put(orange, 12);
    }

    @Test(expected = NullPointerException.class)
    public void apply_null_Ok() {
        supplyHandler.apply(null);
    }

    @Test
    public void apply_validData_Ok() {
        int expected = 20;
        int actual = supplyHandler.apply(fruitRecord);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
