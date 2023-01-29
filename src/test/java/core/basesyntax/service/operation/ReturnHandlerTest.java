package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeClass
    public static void beforeClass() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void getOperationActionTest_GetReturn_OK() {
        int expected = -10;
        int actual = returnHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
