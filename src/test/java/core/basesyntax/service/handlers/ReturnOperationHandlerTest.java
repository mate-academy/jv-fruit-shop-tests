package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final Fruit banana = new Fruit("banana");
    private final OperationHandler returnOperationHandler = new ReturnOperationHandler();

    @BeforeClass
    public static void setUp() {
        Warehouse.getWarehouse().put(banana, 100);
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        returnOperationHandler.handle(banana, 20);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 120);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
