package core.basesyntax.service.functionalityexpansion;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.ActivityHandler;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityHandlerProviderTest {
    private final Storage storage = new Storage();
    private ActivityHandlerProvider handlerProvider;

    @BeforeEach
    void setUp() {
        handlerProvider = new ActivityHandlerProvider(storage);
    }

    @Test
    public void getHandler_handlerBalanceType_Ok() {
        ActivityHandler expectedHandler = handlerProvider.getHandler(ActivityType.BALANCE);
        Assertions.assertEquals(expectedHandler.getClass(), BalanceHandler.class);
    }

    @Test
    public void getHandler_handlerPurchaseType_Ok() {
        ActivityHandler expectedHandler = handlerProvider.getHandler(ActivityType.PURCHASE);
        Assertions.assertEquals(expectedHandler.getClass(), PurchaseHandler.class);
    }

    @Test
    public void getHandler_handlerReturnType_Ok() {
        ActivityHandler expectedHandler = handlerProvider.getHandler(ActivityType.RETURN);
        Assertions.assertEquals(expectedHandler.getClass(), ReturnHandler.class);
    }

    @Test
    public void getHandler_handlerSupplyType_Ok() {
        ActivityHandler expectedHandler = handlerProvider.getHandler(ActivityType.SUPPLY);
        Assertions.assertEquals(expectedHandler.getClass(), SupplyHandler.class);
    }
}
