package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NOT_ENOUGH_QUANTITY = 5;
    private static final int GET_DEFAULT_QUANTITY = 0;
    private static final String DEFAULT_FRUIT = "banana";
    private static final String OPERATION = "p";
    private static OperationHandler purchaseOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void operate_withEnoughFruit() {
        // Arrange
        Storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION,
                DEFAULT_FRUIT, NOT_ENOUGH_QUANTITY);
        purchaseOperationHandler.operate(fruitTransaction);
        assertEquals(5, Storage.getOrDefault(DEFAULT_FRUIT, GET_DEFAULT_QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void operate_withNotEnoughFruit_notOk() {
        Storage.put(DEFAULT_FRUIT, NOT_ENOUGH_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        purchaseOperationHandler.operate(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void operate_withNullInput_notOk() {
        fruitTransaction = null;
        purchaseOperationHandler.operate(fruitTransaction);
    }
}
