package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void validData_ok() {
        fruitTransaction = new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE);
        operationHandler.applyChanges(fruitTransaction);
        int expected = 100;
        int actual = Storage.storage.get(fruitTransaction.getNameOfFruit());
        Assert.assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void nameofFruitNull_notOk() {
        fruitTransaction = new FruitTransaction(null, 100,
                FruitTransaction.Operation.BALANCE);
        operationHandler.applyChanges(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantityOfFruits_notOk() {
        fruitTransaction = new FruitTransaction("apple", -100,
                FruitTransaction.Operation.BALANCE);
        operationHandler.applyChanges(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void doubledBalanceOperation_notOk() {
        Storage.storage.put("apple", 10);
        fruitTransaction = new FruitTransaction("apple", 20,
                FruitTransaction.Operation.BALANCE);
        operationHandler.applyChanges(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
