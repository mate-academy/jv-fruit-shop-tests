package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final FruitTransaction.Operation RETURN_OPERATION =
            FruitTransaction.Operation.RETURN;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullTransaction_notOk() {
        operationHandler.apply(null);
    }

    @Test (expected = RuntimeException.class)
    public void apply_zeroQuantity_notOk() {
        FruitStorage.fruits.put("apple", 0);
        fruitTransaction = new FruitTransaction(RETURN_OPERATION, "apple", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        FruitStorage.fruits.put("orange", 16);
        int expected = 28;
        fruitTransaction = new FruitTransaction(RETURN_OPERATION, "orange", 12);
        operationHandler.apply(fruitTransaction);
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }
}
