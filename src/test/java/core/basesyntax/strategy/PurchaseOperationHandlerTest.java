package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int INITIAL_QUANTITY1 = 20;
    private static final int INITIAL_QUANTITY2 = 10;
    private static final String FRUIT_NAME = "apple";
    private OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY1);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY2);
        purchaseOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY1 - INITIAL_QUANTITY2;
        assertEquals(expectedQuantity, (int) Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null,
                        INITIAL_QUANTITY2);
        purchaseOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, INITIAL_QUANTITY2);
        purchaseOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughQuantity_notOk() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY2);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY1);
        purchaseOperationHandler.handle(fruitTransaction);
    }
}
