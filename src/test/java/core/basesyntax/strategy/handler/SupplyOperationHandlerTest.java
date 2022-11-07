package core.basesyntax.strategy.handler;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.*;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @Before
    public void setUp() {
        supplyHandler = new SupplyOperationHandler(new StorageDaoImpl());
        Storage.storage.put(new Fruit( "apple"), 20);
    }

    @Test
    public void execute_supplyTransaction_ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(SUPPLY, new Fruit("apple"), 10);
        int expected = 30;
        supplyHandler.execute(fruitTransaction);
        int actual = Storage.storage.get("apple");
        assertEquals(expected, actual);
    }


    @Test(expected = NullPointerException.class)
    public void execute_null_ok() {
        supplyHandler.execute(null);
    }
}