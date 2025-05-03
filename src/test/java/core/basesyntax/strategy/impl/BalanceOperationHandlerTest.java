package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void calculate_Ok() {
        Integer expectedResult = 20;
        operationHandler.calculate(new Transaction("banana", 20));
        Integer actual = Storage.fruitsStorage.get("banana");
        assertEquals(expectedResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_NegativeAmount_NotOk() {
        operationHandler.calculate(new Transaction("banana", -50));
    }

    @Test(expected = RuntimeException.class)
    public void calculate_TwiceBalance_NotOk() {
        operationHandler.calculate(new Transaction("banana", 30));
        operationHandler.calculate(new Transaction("banana", 90));
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
