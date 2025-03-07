package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.PurchaseHandler;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    @Test
    void return_Check_IsOk() {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        int result = purchaseHandler.operate(10, 20);
        int expected = 10;
        assertEquals(result, expected);
    }
}
