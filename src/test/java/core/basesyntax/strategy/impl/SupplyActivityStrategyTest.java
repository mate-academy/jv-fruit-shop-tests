package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.ActivityStrategy;
import org.junit.jupiter.api.Test;

class SupplyActivityStrategyTest {
    private final ActivityStrategy activityStrategy = new SupplyActivityStrategy();

    @Test
    void calculateQuantity_Ok() {
        int oldQuantity = 40;
        int activityQuantity = 20;
        int actual = activityStrategy.calculateQuantity(oldQuantity, activityQuantity);
        assertEquals(60, actual);
    }
}
