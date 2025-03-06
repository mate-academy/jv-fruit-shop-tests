package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.BalanceHandler;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    @Test
    void balance_Check_IsOk() {
        BalanceHandler balanceHandler = new BalanceHandler();
        int result = balanceHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }
}
