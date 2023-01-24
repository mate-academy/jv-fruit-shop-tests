package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE_QUANTITY = 100;
    private static final int TEST_VALID_QUANTITY = 20;
    private static final int TEST_INVALID_QUANTITY = 120;
    private static final int EXPECTED_RESULT = 80;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.put(TEST_FRUIT, TEST_BALANCE_QUANTITY);
    }

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit(TEST_FRUIT);
    }

    @Test
    public void handle_addToStorageValidQuantity_ok() {
        fruitTransaction.setQuantity(TEST_VALID_QUANTITY);
        operationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(TEST_FRUIT);
        assertEquals(EXPECTED_RESULT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_addToStorageInvalidQuantity_notOk() {
        fruitTransaction.setQuantity(TEST_INVALID_QUANTITY);
        operationHandler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
