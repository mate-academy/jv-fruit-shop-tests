package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final Integer EXPECTED = 2;
    private static final Integer TRANSACTION_VALUE = 2;
    private static final Integer OLD_VALUE = 10;
    private static final Integer NEGATIVE_VALUE = -10;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balance_ok() {
        Integer actual = operationHandler.operate(TRANSACTION_VALUE, OLD_VALUE);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void transactionValue_negative() {
        operationHandler.operate(NEGATIVE_VALUE, OLD_VALUE);
    }

    @Test(expected = RuntimeException.class)

    public void transactionValue_null() {
        operationHandler.operate(null, null);
    }
}
