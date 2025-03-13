package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReturnHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeAll
    static void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    void operate_BalanceCheck_IsOk() {
        int result = returnHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operate_OperatingAmountNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            returnHandler.operate(-10, 20);
        });
    }
}
