package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler operationHandler = new PurchaseOperationHandler();

    @Before
    public void setUp() {
        OperationHandler balanceHandler = new BalanceOperationHandler();
        balanceHandler.calculate(new Transaction("banana", 30));
        balanceHandler.calculate(new Transaction("apple", 30));
    }

    @Test
    public void calculate_Ok() {
        operationHandler.calculate(new Transaction("apple", 25));
        Integer actual = Storage.fruitsStorage.get("apple");
        Integer expectResult = 5;
        assertEquals(expectResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_NegativeAmount_NotOk() {
        operationHandler.calculate(new Transaction("apple", -25));
    }

    @Test(expected = RuntimeException.class)
    public void calculate_LargerAmount_NotOk() {
        operationHandler.calculate(new Transaction("banana", 40));
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
