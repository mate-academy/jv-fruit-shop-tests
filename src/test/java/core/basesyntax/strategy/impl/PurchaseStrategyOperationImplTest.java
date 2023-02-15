package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseStrategyOperationImplTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.put("banana", 40);
    }

    @Before
    public void setUp() {
        operationHandler = new PurchaseStrategyOperationImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void handle_Ok() {
        Storage.fruits.put("banana", 40);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        Integer expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionNegative_notOk() {
        fruitTransaction.setQuantity(-10);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeResult_notOk() {
        fruitTransaction.setQuantity(100);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_transactionNull_notOk() {
        operationHandler.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_storageFruitNull_notOk() {
        Storage.fruits.put(null, null);
        operationHandler.handle(fruitTransaction);
    }
}
