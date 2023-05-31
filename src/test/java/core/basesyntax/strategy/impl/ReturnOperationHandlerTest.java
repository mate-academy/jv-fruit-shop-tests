package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String DEFAULT_FRUIT = "banana";
    private static final String DEFAULT_OPERATION = "r";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int GET_DEFAULT_QUANTITY = 0;
    private static OperationHandler returnOperationHandler;
    private static FruitTransaction defaultTransaction;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void operate_withExistingFruit_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        returnOperationHandler.operate(fruitTransaction);
        assertEquals(20, Storage.getOrDefault(DEFAULT_FRUIT, GET_DEFAULT_QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void operate_withNullTransaction_notOk() {
        defaultTransaction = null;
        returnOperationHandler.operate(defaultTransaction);
    }
}
