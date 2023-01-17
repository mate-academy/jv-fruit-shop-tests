package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final FruitTransaction.Operation PURCHASE_OPERATION =
            FruitTransaction.Operation.PURCHASE;
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
        FruitStorage.fruits.put("banana", 0);
        fruitTransaction = new FruitTransaction(PURCHASE_OPERATION, "banana", 10);
        operationHandler.apply(fruitTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void apply_zeroQuantity_notOk() {
        FruitStorage.fruits.put("apple", 8);
        fruitTransaction = new FruitTransaction(PURCHASE_OPERATION, "apple", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        FruitStorage.fruits.put("orange", 16);
        int expected = 4;
        fruitTransaction = new FruitTransaction(PURCHASE_OPERATION, "orange", 12);
        operationHandler.apply(fruitTransaction);
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }
}
