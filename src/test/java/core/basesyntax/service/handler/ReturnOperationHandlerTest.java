package core.basesyntax.service.handler;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void returnHandlerInitialization() {
        handler = new ReturnOperationHandler();
    }

    @Test
    public void calculateQuantity_validData_ok() {
        int expected = 103;
        int actual = handler.calculateQuantity(100, 3);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_negativeValue_notOk() {
        handler.calculateQuantity(100, -30);
    }
}
