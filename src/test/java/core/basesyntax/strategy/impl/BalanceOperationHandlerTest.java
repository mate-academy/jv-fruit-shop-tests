package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction CORRECT_TRANSACTION
            = new FruitTransaction(Operation.BALANCE, "banana", 20);
    private static final FruitTransaction INCORRECT_TRANSACTION
            = new FruitTransaction(Operation.RETURN, "banana", 20);
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeClass
    public static void initBalanceHandler() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void makeOperation_validData_ok() {
        assertTrue(balanceOperationHandler.makeOperation(CORRECT_TRANSACTION));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_inValidData_notOk() {
        assertTrue(balanceOperationHandler.makeOperation(INCORRECT_TRANSACTION));
    }
}
