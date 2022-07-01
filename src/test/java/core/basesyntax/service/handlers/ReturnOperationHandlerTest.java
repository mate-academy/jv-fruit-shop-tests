package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    @BeforeClass
    public static void setUp() {
        Fruit banana = new Fruit("banana");
        Warehouse.getWarehouse().put(banana, 100);
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        Fruit banana = new Fruit("banana");
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        returnOperationHandler.handle(banana, 20);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 120);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
