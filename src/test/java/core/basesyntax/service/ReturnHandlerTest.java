package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReturnHandler;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    @Test
    void return_Check_IsOk() {
        ReturnHandler returnHandler = new ReturnHandler();
        int result = returnHandler.operate(10, 20);
        int expected = 30;
        assertEquals(result, expected);
    }
}
