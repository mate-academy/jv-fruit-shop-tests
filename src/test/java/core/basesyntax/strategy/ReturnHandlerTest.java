package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.ReturnHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void returnHandler_ok() {
        int balance = 100;
        int quality = 10;
        int returnResult = balance + quality;
        int actual = returnHandler.handle(balance, quality);
        int expected = returnResult;
        Assert.assertEquals(expected, actual);
    }
}
