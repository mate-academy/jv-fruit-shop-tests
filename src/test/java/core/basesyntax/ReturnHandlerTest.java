package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.ReturnHandler;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @Test
    public void returnHandlerTest_getOperationAction_OK() {
        returnHandler = new ReturnHandler();
        int expected = -10;
        int actual = returnHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
