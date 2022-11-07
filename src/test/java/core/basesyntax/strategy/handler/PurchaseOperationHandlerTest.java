package core.basesyntax.strategy.handler;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.Assert.assertEquals;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseOperationHandler(new StorageDaoImpl());
        Storage.storage.put(new Fruit("apple"), 25);
    }

    @Test
    public void execute_purchaseTransaction_ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(PURCHASE, "apple", 10);
        int expected = 15;
        purchaseHandler.execute(fruitTransaction);
        int actual = Storage.storage.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void execute_purchaseTransaction_notOk() {
        FruitTransaction fruitTransaction = FruitTransaction.of(PURCHASE, "apple", 35);
        int expected = -15;
        purchaseHandler.execute(fruitTransaction);
        int actual = Storage.storage.get("apple");
        assertEquals(expected, actual);
    }
}