package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.SupplyHandler;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private SupplyHandler supplyHandler;

    @Test
    void supply_Check_IsOk() {
        supplyHandler = new SupplyHandler();
        int result = supplyHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operAmount_Negative_NotOk() {
        supplyHandler = new SupplyHandler();
        assertThrows(RuntimeException.class, () -> {
            supplyHandler.operate(-10, 20);
        });
    }
}
