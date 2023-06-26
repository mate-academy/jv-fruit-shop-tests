package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Integer TRANSACTION_VALUE = 100;
    private static final Integer OLD_VALUE = 90;
    private static final Integer EXPECTED_RESULT = 190;
    private static final Integer NEGATIVE_TRANSACTION_VALUE = -100;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeAll() {
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void operationHandlerTest_ok() {
        assertEquals(EXPECTED_RESULT, supplyHandler.operate(TRANSACTION_VALUE, OLD_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void operationHandlerTest_negativeValue_notOk() {
        supplyHandler.operate(NEGATIVE_TRANSACTION_VALUE, OLD_VALUE);
    }
}
