package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QUANTITY = 20;
    private FruitTransaction fruitTransaction;
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Before
    public void crateTransaction() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_QUANTITY);
    }

    @Test
    public void perfomOperation_ok() {
        balanceOperationHandler.perfomOperation(fruitTransaction);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(TEST_QUANTITY));
    }

    @AfterClass
    public static void cleanStorage() {
        Storage.fruits.clear();
    }
}
