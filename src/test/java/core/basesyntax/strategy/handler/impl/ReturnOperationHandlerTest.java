package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final Integer TRANSACTION_VALUE = 5;
    private static final Integer OLD_VALUE = 25;
    private static final Integer EXPECTED_VALUE = 30;
    private static final Integer NEGATIVE_VALUE = -1;
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeAll() {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    public void return_ok() {
        assertEquals(EXPECTED_VALUE, returnHandler.operate(TRANSACTION_VALUE, OLD_VALUE));

    }

    @Test (expected = RuntimeException.class)
    public void negativeAmount_notOk() {
        returnHandler.operate(NEGATIVE_VALUE, OLD_VALUE);
    }

    @Test (expected = RuntimeException.class)
    public void nullAmount_notOK() {
        returnHandler.operate(null, null);
    }
}
