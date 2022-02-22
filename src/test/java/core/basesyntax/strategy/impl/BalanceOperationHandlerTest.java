package core.basesyntax.strategy.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final int OPERATION_COUNT = 2;
    private static OperationHandler operationHandler;
    private static FruitTransaction transactionOk;
    private static FruitTransaction transactionWithBadQuantity;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new BalanceOperationHandler();
        transactionOk = new FruitTransaction();
        transactionWithBadQuantity = new FruitTransaction();
    }

    @Before
    public void setUp() {
        transactionOk.setFruit("apple");
        transactionOk.setOperation(FruitTransaction.Operation.BALANCE);
        transactionOk.setQuantity(50);
        transactionWithBadQuantity.setFruit("banana");
        transactionWithBadQuantity.setOperation(FruitTransaction.Operation.BALANCE);
        transactionWithBadQuantity.setQuantity(-50);
    }

    @Test
    public void updateQuantity_ok() {
        for (int i = 0; i < OPERATION_COUNT; i++) {
            operationHandler.updateQuantity(transactionOk);
        }
        int actual = Storage.fruits.get(0).getQuantity();
        int expected = transactionOk.getQuantity() * OPERATION_COUNT;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateNegativeQuantity_notOk() {
        operationHandler.updateQuantity(transactionWithBadQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
