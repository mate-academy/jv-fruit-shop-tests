package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static String TEST_FRUIT;
    private static int TEST_QUANTITY;
    private static OperationHandler balanceOperationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void initialize_var() {
        TEST_FRUIT = "apple";
        TEST_QUANTITY = 20;
        balanceOperationHandler = new BalanceOperationHandler();
    }

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
