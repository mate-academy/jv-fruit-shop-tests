package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.ActivityStrategy;
import org.junit.jupiter.api.Test;

class BalanceActivityStrategyTest {
    private final ActivityStrategy activityStrategy = new BalanceActivityStrategy();

    @Test
    void calculateQuantity_Ok() {
        int oldQuantity = 40;
        int activityQuantity = 80;
        int actual = activityStrategy.calculateQuantity(oldQuantity, activityQuantity);
        assertEquals(80, actual);
    }

    @Test
    void calculateQuantity_zero_isOk() {
        int oldQuantity = 0;
        int activityQuantity = 0;
        int actual = activityStrategy.calculateQuantity(oldQuantity, activityQuantity);
        assertEquals(0, actual);
    }
}
