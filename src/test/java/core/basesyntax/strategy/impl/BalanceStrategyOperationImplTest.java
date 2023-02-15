package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceStrategyOperationImplTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BalanceStrategyOperationImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void handle_Ok() {
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        Integer expected = 20;
        Assert.assertEquals(expected, actual);

    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionNegative_notOk() {
        fruitTransaction.setQuantity(-10);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeResult_notOk() {
        Storage.fruits.put("banana", -1000);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionNull_notOk() {
        operationHandler.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionOperationNull_notOk() {
        fruitTransaction.setOperation(null);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionFruitNull_notOk() {
        fruitTransaction.setFruit(null);
        operationHandler.handle(fruitTransaction);
    }
}
