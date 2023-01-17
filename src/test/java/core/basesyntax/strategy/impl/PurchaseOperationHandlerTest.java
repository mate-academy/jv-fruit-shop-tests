package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;
    private static final Map<String, Integer> STORAGE = FruitStorage.fruits;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullTransaction_notOk() {
        operationHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_quantityIsBiggerThanBalance_notOk() {
        STORAGE.put("banana", 0);
        fruitTransaction = new FruitTransaction(PURCHASE, "banana", 10);
        operationHandler.apply(fruitTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void apply_zeroQuantity_notOk() {
        STORAGE.put("apple", 8);
        fruitTransaction = new FruitTransaction(PURCHASE, "apple", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        STORAGE.put("orange", 16);
        int expected = 4;
        fruitTransaction = new FruitTransaction(PURCHASE, "orange", 12);
        operationHandler.apply(fruitTransaction);
        int actual = STORAGE.get("orange");
        assertEquals(expected, actual);
    }
}
