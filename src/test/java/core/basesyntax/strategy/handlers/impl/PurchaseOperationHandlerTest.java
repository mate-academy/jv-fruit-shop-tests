package core.basesyntax.strategy.handlers.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    public static final Map<String, Integer> testMap = new HashMap<>();
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test(expected = RuntimeException.class)
    public void purchase_NullValue_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana",20);
        purchaseOperationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_NegativeValue_NotOK() {
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",100);
        balanceOperationHandler.handle(fruit1);
        purchaseOperationHandler.handle(fruit2);
        System.out.println(Storage.storage);
    }

    @Test
    public void purchase_correct_OK() {
        testMap.put("banana",0);
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        balanceOperationHandler.handle(fruit1);
        purchaseOperationHandler.handle(fruit2);
        Assert.assertEquals(testMap,Storage.storage);
    }

    @Test
    public void purchase_correct_NotOK() {
        testMap.put("banana",0);
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",10);
        balanceOperationHandler.handle(fruit1);
        purchaseOperationHandler.handle(fruit2);
        Assert.assertNotEquals(testMap,Storage.storage);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
