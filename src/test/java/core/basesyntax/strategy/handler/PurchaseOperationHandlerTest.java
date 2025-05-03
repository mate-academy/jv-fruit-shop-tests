package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseOperationHandler(new StorageDaoImpl());
    }

    @Test(expected = RuntimeException.class)
    public void execute_notEnoughInStock_ok() {
        Fruit banana = new Fruit("banana");
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 100);
        purchaseHandler.execute(transaction);
    }

    @Test
    public void execute_validDataAndNotEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 120);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 100);
        purchaseHandler.execute(transaction);
        int expected = 20;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
