package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    private static OperationHandler balanceHandler;
    private static final Integer EXPECTED = 2;
    private static final Integer TRANSACTION_VALUE = 2;
    private static final Integer OLD_VALUE = 10;

    @BeforeClass
    public static void beforeAll() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void balance_ok() {
        Integer actual = balanceHandler.operate(TRANSACTION_VALUE,OLD_VALUE);
        assertEquals(EXPECTED,actual);
    }
}
