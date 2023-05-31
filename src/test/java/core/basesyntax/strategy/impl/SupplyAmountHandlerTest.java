package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AmountHandler;
import org.junit.Assert;
import org.junit.Test;

public class SupplyAmountHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);

    @Test
    public void supplyAmountHandler_usingStrategy_ok() {
        AmountHandler amountHandler = new SupplyAmountHandler();
        int actual = amountHandler.changeAmount(TEST_FRUIT, 10);
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }
}
