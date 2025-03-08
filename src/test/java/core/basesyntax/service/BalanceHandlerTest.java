package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.BalanceHandler;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @Test
    void balance_Check_IsOk() {
        balanceHandler = new BalanceHandler();
        int result = balanceHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operAmount_Negative_NotOk() {
        balanceHandler = new BalanceHandler();
        assertThrows(RuntimeException.class, () -> {
            balanceHandler.operate(-10, 20);
        });
    }
}
