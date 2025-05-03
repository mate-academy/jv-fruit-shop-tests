package core.basesyntax.service.handler;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnHandler = new ReturnHandler();
    }

    @Test(expected = RuntimeException.class)
    public void applyNullValue_NotOk() {
        returnHandler.apply(null);
    }
}
