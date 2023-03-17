package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static BalanceOperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @After
    public void afterEach() {
        Storage.clear();
    }

    @Test (expected = RuntimeException.class)
    public void doActivity_fruitIsNull_notOk() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION, null, 123);
        balanceOperationHandler.doActivity(fruitTransaction);
    }

    @Test
    public void doActivity_defaultCase_ok() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION, "banana", 20);
        Integer expectedQuantity = 20;
        balanceOperationHandler.doActivity(fruitTransaction);
        Integer actualQuantity = Storage.get(fruitTransaction.getFruit());
        assertEquals("Must be correct data in storage ", expectedQuantity, actualQuantity);
    }
}
