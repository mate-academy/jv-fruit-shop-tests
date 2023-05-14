package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerReturnTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnHandler = new OperationHandlerReturn();
    }

    @Test
    public void changeSaldo_validValue() {
        assertEquals(returnHandler.changeSaldo(10).applyAsInt(5),15);
        assertEquals(returnHandler.changeSaldo(5).applyAsInt(0),5);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidSaldo_notOk() {
        returnHandler.changeSaldo(-1).applyAsInt(10);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidQuantity_notOk() {
        returnHandler.changeSaldo(10).applyAsInt(-1);
    }
}
