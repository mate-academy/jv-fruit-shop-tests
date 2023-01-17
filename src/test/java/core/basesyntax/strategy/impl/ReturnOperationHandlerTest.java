package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static final Map<String, Integer> STORAGE = FruitStorage.fruits;
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
        STORAGE.put("apple", 0);
        fruitTransaction = new FruitTransaction(RETURN, "apple", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        STORAGE.put("orange", 16);
        int expected = 28;
        fruitTransaction = new FruitTransaction(RETURN, "orange", 12);
        operationHandler.apply(fruitTransaction);
        int actual = STORAGE.get("orange");
        assertEquals(expected, actual);
    }
}
