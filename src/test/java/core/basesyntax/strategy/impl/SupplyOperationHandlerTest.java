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

public class SupplyOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE_QUANTITY = 50;
    private static final int TEST_VALID_QUANTITY = 40;
    private static final int EXPECTED_RESULT = 90;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.put(TEST_FRUIT, TEST_BALANCE_QUANTITY);
    }

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(Operation.RETURN);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_VALID_QUANTITY);
    }

    @Test
    public void handle_addToStorageSupplyFruit_ok() {
        operationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(TEST_FRUIT);
        assertEquals(EXPECTED_RESULT, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
