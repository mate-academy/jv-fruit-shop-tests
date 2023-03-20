package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    public void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        10);
        purchaseOperationHandler.handle(fruitTransaction);
        Integer expectedQuantity = 20 - 10;
        assertEquals(expectedQuantity, Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null,
                        10);
        purchaseOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, 10);
        purchaseOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughQuantity_notOk() {
        Storage.storage.put(FRUIT_NAME, 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        20);
        purchaseOperationHandler.handle(fruitTransaction);
    }
}
