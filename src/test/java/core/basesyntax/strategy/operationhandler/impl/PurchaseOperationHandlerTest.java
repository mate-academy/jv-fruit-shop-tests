package core.basesyntax.strategy.operationhandler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int QUANTITY = 50;
    private static final int PURCHASE_QUANTITY = 30;
    private static final int GREATER_PURCHASE_QUANTITY = 51;
    private static FruitTransaction transaction;
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void init() {
        transaction = new FruitTransaction();
        purchaseOperationHandler = new PurchaseOperationHandler();
        FruitStorage.fruitStorage.put(APPLE, QUANTITY);
        transaction.setFruit(APPLE);
    }

    @Test
    public void handle_isOk() {
        transaction.setQuantity(PURCHASE_QUANTITY);
        purchaseOperationHandler.handle(transaction);
        int actual = FruitStorage.fruitStorage.get(APPLE);
        int expected = QUANTITY - PURCHASE_QUANTITY;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void handle_greaterPurchaseQuantity_notOk() {
        transaction.setQuantity(GREATER_PURCHASE_QUANTITY);
        purchaseOperationHandler.handle(transaction);
    }
}
