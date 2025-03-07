package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.SupplyHandler;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    @Test
    void supply_Check_IsOk() {
        SupplyHandler supplyHandler = new SupplyHandler();
        int result = supplyHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }
}
