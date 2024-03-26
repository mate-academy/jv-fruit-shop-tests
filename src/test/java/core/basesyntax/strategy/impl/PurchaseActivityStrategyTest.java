package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.ActivityStrategy;
import org.junit.jupiter.api.Test;

class PurchaseActivityStrategyTest {
    private final ActivityStrategy activityStrategy = new PurchaseActivityStrategy();

    @Test
    void calculateQuantity_Ok() {
        int oldQuantity = 80;
        int activityQuantity = 20;
        int actual = activityStrategy.calculateQuantity(oldQuantity, activityQuantity);
        assertEquals(60, actual);
    }
}
