package core.basesyntax.strategy.handlers.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    public static final Map<String, Integer> testMap = new HashMap<>();
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test(expected = RuntimeException.class)
    public void supply_NullValue_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana",20);
        supplyOperationHandler.handle(fruitTransaction);
        System.out.println(Storage.storage);
    }

    @Test
    public void supply_correct_OK() {
        testMap.put("banana",40);
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        balanceOperationHandler.handle(fruit1);
        supplyOperationHandler.handle(fruit2);
        Assert.assertEquals(testMap,Storage.storage);
    }

    @Test
    public void supply_correct_NotOK() {
        testMap.put("banana",0);
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        balanceOperationHandler.handle(fruit1);
        supplyOperationHandler.handle(fruit2);
        Assert.assertNotEquals(testMap,Storage.storage);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
