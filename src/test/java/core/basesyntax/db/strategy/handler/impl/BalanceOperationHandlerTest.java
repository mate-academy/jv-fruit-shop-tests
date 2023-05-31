package core.basesyntax.db.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final Integer DEFAULT_QUANTITY = 1;
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void apply_addCorrectDataToStorage_ok() {
        operationHandler.apply(new FruitTransaction(OPERATION, "banana", DEFAULT_QUANTITY));
        Integer actual = Storage.getAll().get("banana");
        assertEquals(DEFAULT_QUANTITY, actual);
    }

    @Test (expected = NullPointerException.class)
    public void apply_addDataWithNullKey_notOk() {
        operationHandler.apply(new FruitTransaction(OPERATION, null, DEFAULT_QUANTITY));
    }

    @Test (expected = RuntimeException.class)
    public void apply_addDataWithEmptyKey_ok() {
        operationHandler.apply(new FruitTransaction(OPERATION, "", DEFAULT_QUANTITY));
    }

    @After
    public void tearDown() {
        Storage.getAll().clear();
    }
}
