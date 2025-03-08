package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReturnHandler;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @Test
    void return_Check_IsOk() {
        returnHandler = new ReturnHandler();
        int result = returnHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }

    @Test
    void operAmount_Negative_NotOk() {
        returnHandler = new ReturnHandler();
        assertThrows(RuntimeException.class, () -> {
            returnHandler.operate(-10, 20);
        });
    }
}
