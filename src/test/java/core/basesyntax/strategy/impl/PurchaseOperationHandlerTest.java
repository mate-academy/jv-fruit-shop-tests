package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void validData_Ok() {
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction("banana",
                10, FruitTransaction.Operation.PURCHASE);
        operationHandler.applyChanges(fruitTransaction);
        int expected = 10;
        int actual = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void negativePurchaseQuantity_notOk() {
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction("banana",
                -10, FruitTransaction.Operation.PURCHASE);
        operationHandler.applyChanges(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void balanceQuantityNotEnough_notOk() {
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction("banana",
                30, FruitTransaction.Operation.PURCHASE);
        operationHandler.applyChanges(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
