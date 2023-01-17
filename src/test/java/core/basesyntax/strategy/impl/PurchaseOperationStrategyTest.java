package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationStrategyTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_ok() {
        Storage.fruits.put("banana", 60);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 50);
        purchaseOperationHandler.calculate(transaction);
        Integer expected = 10;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals("Wrong purchase data", expected, actual);
    }

    @Test
    public void calculate_notEnoughQuantity_notOk() {
        Storage.fruits.put("banana", 40);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 50);
        purchaseOperationHandler.calculate(transaction);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
