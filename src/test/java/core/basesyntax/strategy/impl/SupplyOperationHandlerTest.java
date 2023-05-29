package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final FruitTransaction.Operation SUPPLY_OPERATION =
            FruitTransaction.Operation.SUPPLY;
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
        FruitStorage.fruits.put("orange", 2);
        fruitTransaction = new FruitTransaction(SUPPLY_OPERATION, "orange", 0);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_validQuantity_ok() {
        FruitStorage.fruits.put("banana", 8);
        int expected = 13;
        fruitTransaction = new FruitTransaction(SUPPLY_OPERATION, "banana", 5);
        operationHandler.apply(fruitTransaction);
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(expected, actual);
    }
}
