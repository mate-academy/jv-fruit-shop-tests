package strategy.impl;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.OperationHandler;

public class PurchaseOperationHandlerTest {
    private static final int BANANA_QUANTITY = 70;
    private static final int INVALID_BANANA_QUANTITY = 200;
    private static final int PURCHASE_BANANA_QUANTITY = 30;
    private static final String BANANA = "banana";
    private static final FruitTransaction expected =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    private static OperationHandler operationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_Ok() {
        operationHandler.calculate(expected);
        purchaseOperationHandler.calculate(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        BANANA, PURCHASE_BANANA_QUANTITY));
        int actualBanana = Storage.fruits.get(BANANA);
        Assert.assertEquals(
                "Some problem with PurchaseOperationHandler",
                BANANA_QUANTITY, actualBanana);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_invalidData_notOk() {
        operationHandler.calculate(expected);
        purchaseOperationHandler.calculate(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        BANANA, INVALID_BANANA_QUANTITY));
    }
}
