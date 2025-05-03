package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlersSupplyTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyHandler = new OperationHandlersSupply();
    }

    @Test
    public void changeSaldo_validValue() {
        assertEquals(supplyHandler.changeSaldo(10).applyAsInt(5),15);
        assertEquals(supplyHandler.changeSaldo(5).applyAsInt(0),5);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidSaldo_notOk() {
        supplyHandler.changeSaldo(-1).applyAsInt(10);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidQuantity_notOk() {
        supplyHandler.changeSaldo(10).applyAsInt(-1);
    }
}
