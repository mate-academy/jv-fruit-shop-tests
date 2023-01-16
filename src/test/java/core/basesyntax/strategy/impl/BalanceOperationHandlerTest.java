package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final Integer DEFAULT_AMOUNT = 10;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void apply_addCorrectValueToStorage_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", DEFAULT_AMOUNT));
        Integer actual = FruitStorage.fruits.get("banana");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                DEFAULT_AMOUNT, "banana", actual), DEFAULT_AMOUNT, actual);
    }

    @Test
    public void apply_addDataWithNullKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, DEFAULT_AMOUNT));
        Integer actual = FruitStorage.fruits.get(null);
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                DEFAULT_AMOUNT, "banana", actual), DEFAULT_AMOUNT, actual);
    }

    @Test
    public void apply_addDataWithEmptyKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "", DEFAULT_AMOUNT));
        Integer actual = FruitStorage.fruits.get("");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                DEFAULT_AMOUNT, "banana", actual), DEFAULT_AMOUNT, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
