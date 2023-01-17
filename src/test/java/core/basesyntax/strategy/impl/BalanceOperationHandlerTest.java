package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullTransaction_notOk() {
        operationHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeQuantity_notOk() {
        fruitTransaction = new FruitTransaction(BALANCE, "banana", -10);
        operationHandler.apply(fruitTransaction);
    }

    @Test
    public void apply_zeroQuantity_ok() {
        int expected = 0;
        fruitTransaction = new FruitTransaction(BALANCE, "apple", expected);
        operationHandler.apply(fruitTransaction);
        int actual = FruitStorage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void apply_validQuantity_ok() {
        int expected = 12;
        fruitTransaction = new FruitTransaction(BALANCE, "orange", expected);
        operationHandler.apply(fruitTransaction);
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }
}
