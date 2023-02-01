package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QUANTITY = 20;
    private static final int NEW_TEST_QUANTITY = 15;
    private FruitTransaction fruitTransaction;
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler();

    @Before
    public void fillStorage() {
        Storage.fruits.put(TEST_FRUIT, TEST_QUANTITY);
    }

    @Before
    public void crateTransaction() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(NEW_TEST_QUANTITY);
    }

    @Test
    public void perfomOperation_ok() {
        supplyOperationHandler.perfomOperation(fruitTransaction);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(TEST_QUANTITY + NEW_TEST_QUANTITY));
    }

    @AfterClass
    public static void cleanStorage() {
        Storage.fruits.clear();
    }
}
