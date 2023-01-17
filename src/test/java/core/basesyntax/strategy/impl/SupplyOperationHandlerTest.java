package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static final Map<String, Integer> STORAGE = FruitStorage.fruits;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullTransaction_notOk() {
        operationHandler.apply(null);
    }

    @Test (expected = RuntimeException.class)
    public void apply_zeroQuantity_notOk() {
        STORAGE.put("orange", 2);
        fruitTransaction = new FruitTransaction(SUPPLY, "orange", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        STORAGE.put("banana", 8);
        int expected = 13;
        fruitTransaction = new FruitTransaction(SUPPLY, "banana", 5);
        operationHandler.apply(fruitTransaction);
        int actual = STORAGE.get("banana");
        assertEquals(expected, actual);
    }
}
