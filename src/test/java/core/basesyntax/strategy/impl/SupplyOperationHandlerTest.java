package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String DEFAULT_FRUIT = "banana";
    private static final String DEFAULT_OPERATION = "s";
    private static final int GET_DEFAULT_QUANTITY = 0;
    private static final int DEFAULT_QUANTITY = 10;
    private static OperationHandler supplyHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyOperationHandler();
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    public void testOperate_singleTransaction_ok() {
        Storage.getMap().clear();
        supplyHandler.operate(fruitTransaction);
        assertEquals(DEFAULT_QUANTITY, Storage.getOrDefault(DEFAULT_FRUIT, GET_DEFAULT_QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void testOperate_nullTransaction_notOk() {
        fruitTransaction = null;
        supplyHandler.operate(fruitTransaction);
    }
}
