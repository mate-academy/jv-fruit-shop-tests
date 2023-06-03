package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.ReturnOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final Integer TRANSACTION_VALUE = 5;
    private static final Integer OLD_VALUE = 25;
    private static final Integer EXPECTED_VALUE = 30;
    private static final Integer NEGATIVE_VALUE = -5;
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeAll() {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    public void return_ok() {
        assertEquals(EXPECTED_VALUE, returnHandler.operate(TRANSACTION_VALUE, OLD_VALUE));
    }

    @Test(expected = RuntimeException.class)
    public void transactionValue_negative_notOk() {
        returnHandler.operate(NEGATIVE_VALUE, OLD_VALUE);
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantity_notOk() {
        returnHandler.operate(null, null);
    }
}
