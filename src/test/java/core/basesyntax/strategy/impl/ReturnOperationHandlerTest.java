package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private final OperationHandler operationHandler = new ReturnOperationHandler();

    @Before
    public void setUp() {
        OperationHandler balanceHandler = new BalanceOperationHandler();
        balanceHandler.calculate(new Transaction("banana", 30));
        balanceHandler.calculate(new Transaction("apple", 30));
    }

    @Test
    public void calculate_Ok() {
        operationHandler.calculate(new Transaction("apple", 20));
        Integer actual = Storage.fruitsStorage.get("apple");
        Integer expectResult = 50;
        assertEquals(expectResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_NegativeAmount_NotOk() {
        operationHandler.calculate(new Transaction("apple", -20));
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
