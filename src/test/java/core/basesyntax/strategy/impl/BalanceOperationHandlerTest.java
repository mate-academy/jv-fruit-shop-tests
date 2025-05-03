package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final int DEFAULT_QUANTITY = 10;
    private static final String DEFAULT_FRUIT = "banana";
    private static final String DEFAULT_OPERATION = "b";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.getMap().clear();
    }

    @Test
    public void testOperate_addToFruitQuantity_ok() {
        FruitTransaction defaultFruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        operationHandler.operate(defaultFruitTransaction);
        assertEquals(DEFAULT_QUANTITY, Storage.getOrDefault(DEFAULT_FRUIT, 0));
    }

    @Test(expected = RuntimeException.class)
    public void testOperate_addToFruitNull_notOk() {
        fruitTransaction = null;
        operationHandler.operate(fruitTransaction);
    }
}
