package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void validData_ok() {
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction("banana",
                10, FruitTransaction.Operation.RETURN);
        operationHandler.applyChanges(fruitTransaction);
        int expected = 30;
        int actual = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantity_notOk() {
        fruitTransaction = new FruitTransaction("apple",
                -10, FruitTransaction.Operation.RETURN);
        operationHandler.applyChanges(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void unnamedFruit_notOk() {
        fruitTransaction = new FruitTransaction(null,
                10, FruitTransaction.Operation.RETURN);
        operationHandler.applyChanges(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
