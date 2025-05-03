package core.basesyntax.strategy.handlers.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    public static final Map<String, Integer> testMap = new HashMap<>();
    private final OperationHandler returnOperationHandler = new ReturnOperationHandler();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test(expected = RuntimeException.class)
    public void return_NullValue_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana",20);
        returnOperationHandler.handle(fruitTransaction);
    }

    @Test
    public void return_correct_OK() {
        testMap.put("banana",40);
        FruitTransaction fruit1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        FruitTransaction fruit2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        balanceOperationHandler.handle(fruit1);
        returnOperationHandler.handle(fruit2);
        Assert.assertEquals(testMap,Storage.storage);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }

}
