package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static final Integer VALID_VALUE = 10;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void apply_addCorrectValueToStorage_ok() {
        operationHandler.apply(new FruitTransaction(BALANCE, "banana", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("banana");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @Test
    public void apply_addDataWithNullKey_ok() {
        operationHandler.apply(new FruitTransaction(BALANCE, null, VALID_VALUE));
        Integer actual = FruitStorage.fruits.get(null);
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @Test
    public void apply_addDataWithEmptyKey_ok() {
        operationHandler.apply(new FruitTransaction(BALANCE, "", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
