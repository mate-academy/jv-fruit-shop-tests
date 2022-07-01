package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Fruit banana = new Fruit("banana");
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();

    @BeforeClass
    public static void setUp() {
        Warehouse.getWarehouse().put(banana, 100);
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        purchaseOperationHandler.handle(banana, 30);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 70);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
