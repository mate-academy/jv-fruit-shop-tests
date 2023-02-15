package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyStrategyOperationImplTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new SupplyStrategyOperationImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
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
