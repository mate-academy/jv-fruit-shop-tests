package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static Fruit BANANA;
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void setUp() {
        BANANA = new Fruit("banana");
        supplyOperationHandler = new SupplyOperationHandler();
        Warehouse.getWarehouse().put(BANANA, 100);
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        supplyOperationHandler.handle(BANANA, 50);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(BANANA, 150);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
