package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final FruitTransaction purchaseFruitTransaction
            = new FruitTransaction(Operation.PURCHASE, "apple", 100);
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseHandler();
    }

    @Test
    public void getOperationResult_purchaseOperation_ok() {
        purchaseFruitTransaction.setQuantity(-100);
        FruitTransaction actualFruitTransaction
                = operationHandler.getOperationResult(purchaseFruitTransaction);
        assertEquals(purchaseFruitTransaction, actualFruitTransaction);
    }
}
