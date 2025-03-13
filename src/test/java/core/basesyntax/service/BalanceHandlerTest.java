package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.BalanceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeAll
    static void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    void operate_BalanceCheck_IsOk() {
        int result = balanceHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operate_OperatingAmountNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            balanceHandler.operate(-10, 20);
        });
    }
}
