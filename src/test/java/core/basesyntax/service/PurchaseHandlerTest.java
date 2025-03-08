package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.PurchaseHandler;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @Test
    void return_Check_IsOk() {
        purchaseHandler = new PurchaseHandler();
        int result = purchaseHandler.operate(10, 20);
        int expected = 10;
        assertEquals(result, expected);
    }

    @Test
    void operAmount_Negative_NotOk() {
        purchaseHandler = new PurchaseHandler();
        assertThrows(RuntimeException.class, () -> {
            purchaseHandler.operate(-10, 20);
        });
    }
}
