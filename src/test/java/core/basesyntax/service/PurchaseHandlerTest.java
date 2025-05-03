package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.PurchaseHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeAll
    static void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    void operate_BalanceCheck_IsOk() {
        int result = purchaseHandler.operate(10, 20);
        int expected = 10;
        assertEquals(result, expected);
    }

    @Test
    void operate_AmountNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseHandler.operate(-10, 20);
        });
    }
}
