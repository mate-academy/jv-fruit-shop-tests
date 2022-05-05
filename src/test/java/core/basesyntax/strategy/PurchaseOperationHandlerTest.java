package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void handlePurchaseOperation_ok() {
        Storage.fruitStorage.put(new Fruit("apple"), 50);
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 10);
        operationHandler.process(fruitTransaction);
        int expected = 40;
        int actual = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperationWithNotEnoughFruits_notOk() {
        Storage.fruitStorage.put(new Fruit("apple"), 50);
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 60);
        operationHandler.process(fruitTransaction);
    }

    @After
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
