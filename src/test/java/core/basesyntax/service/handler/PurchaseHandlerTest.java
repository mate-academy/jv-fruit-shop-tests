package core.basesyntax.service.handler;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        purchaseHandler = new PurchaseHandler();
    }

    @Test(expected = RuntimeException.class)
    public void applyNullValue_NotOk() {
        purchaseHandler.apply(null);
    }
}
