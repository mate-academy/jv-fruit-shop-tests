package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static String TEST_FRUIT;
    private static int TEST_QUANTITY;
    private static int NEW_TEST_QUANTITY;
    private static OperationHandler purchaceOperationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void initialize_var() {
        TEST_FRUIT = "apple";
        TEST_QUANTITY = 20;
        NEW_TEST_QUANTITY = 15;
        purchaceOperationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(NEW_TEST_QUANTITY);
        Storage.fruits.put(TEST_FRUIT, TEST_QUANTITY);
    }

    @Test
    public void perfomOperation_ok() {
        purchaceOperationHandler.perfomOperation(fruitTransaction);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(TEST_QUANTITY - NEW_TEST_QUANTITY));
    }

    @Test
    public void perfomOperation_notOk() {
        fruitTransaction.setQuantity(TEST_QUANTITY + NEW_TEST_QUANTITY);
        assertThrows(RuntimeException
                .class, () -> purchaceOperationHandler.perfomOperation(fruitTransaction));
    }

    @AfterClass
    public static void cleanStorage() {
        Storage.fruits.clear();
    }
}
