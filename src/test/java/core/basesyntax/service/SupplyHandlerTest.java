package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.SupplyHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @Test
    void operate_BalanceCheck_IsOk() {
        int result = supplyHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operate_OperatingAmountNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            supplyHandler.operate(-10, 20);
        });
    }
}
