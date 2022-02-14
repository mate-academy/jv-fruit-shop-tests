package core.basesyntax.service.handler;

import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyHandler = new SupplyHandler();
    }

    @Test(expected = RuntimeException.class)
    public void applyNullValue_NotOk() {
        supplyHandler.apply(null);
    }
}
