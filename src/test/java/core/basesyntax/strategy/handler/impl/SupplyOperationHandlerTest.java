package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {

    private static final Integer TRANSACTION_VALUE = 50;
    private static final Integer OLD_VALUE = 90;
    private static final Integer EXPECTED_VALUE = 140;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeAll() {
        supplyHandler = new SupplyOperationHandler();

    }

    @Test
    public void return_ok() {
        assertEquals(EXPECTED_VALUE, supplyHandler.operate(TRANSACTION_VALUE,OLD_VALUE));

    }

}
