package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    @BeforeClass
    public static void setUp() {
        Fruit banana = new Fruit("banana");
        Warehouse.getWarehouse().put(banana, 100);
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        Fruit banana = new Fruit("banana");
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.handle(banana, 200);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 200);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
