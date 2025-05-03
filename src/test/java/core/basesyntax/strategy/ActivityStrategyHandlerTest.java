package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.enums.ActivityType;
import core.basesyntax.exception.UnsupportedActivityException;
import core.basesyntax.strategy.impl.BalanceActivityStrategy;
import core.basesyntax.strategy.impl.PurchaseActivityStrategy;
import core.basesyntax.strategy.impl.ReturnActivityStrategy;
import core.basesyntax.strategy.impl.SupplyActivityStrategy;
import org.junit.jupiter.api.Test;

class ActivityStrategyHandlerTest {
    private final ActivityStrategyHandler handler = new ActivityStrategyHandler();

    @Test
    void get_balance_Ok() {
        ActivityStrategy actualStrategy = handler.get(ActivityType.BALANCE);
        assertInstanceOf(BalanceActivityStrategy.class, actualStrategy);
    }

    @Test
    void get_purchase_Ok() {
        ActivityStrategy actualStrategy = handler.get(ActivityType.PURCHASE);
        assertInstanceOf(PurchaseActivityStrategy.class, actualStrategy);
    }

    @Test
    void get_return_Ok() {
        ActivityStrategy actualStrategy = handler.get(ActivityType.RETURN);
        assertInstanceOf(ReturnActivityStrategy.class, actualStrategy);
    }

    @Test
    void get_supply_Ok() {
        ActivityStrategy actualStrategy = handler.get(ActivityType.SUPPLY);
        assertInstanceOf(SupplyActivityStrategy.class, actualStrategy);
    }

    @Test
    void get_notExist_notOk() {
        ActivityType notExistActivity = ActivityType.findByCode("notExist");
        UnsupportedActivityException existsException = assertThrows(
                UnsupportedActivityException.class,
                () -> handler.get(notExistActivity)
        );
        assertEquals("Unsupported activity: " + notExistActivity,
                existsException.getMessage());
    }

    @Test
    void get_null_notOk() {
        ActivityType nullActivity = null;
        UnsupportedActivityException existsException = assertThrows(
                UnsupportedActivityException.class,
                () -> handler.get(nullActivity)
        );
        assertEquals("Unsupported activity: " + nullActivity,
                existsException.getMessage());
    }
}
