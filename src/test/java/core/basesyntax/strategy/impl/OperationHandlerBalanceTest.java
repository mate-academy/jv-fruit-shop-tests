package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerBalanceTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceHandler = new OperationHandlerBalance();
    }

    @Test
    public void changeSaldo_validValue() {
        assertEquals(balanceHandler.changeSaldo(10).applyAsInt(5),15);
        assertEquals(balanceHandler.changeSaldo(5).applyAsInt(0),5);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidSaldo_notOk() {
        balanceHandler.changeSaldo(-1).applyAsInt(10);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidQuantity_notOk() {
        balanceHandler.changeSaldo(10).applyAsInt(-1);
    }
}
