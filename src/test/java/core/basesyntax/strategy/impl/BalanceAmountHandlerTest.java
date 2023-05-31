package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AmountHandler;
import org.junit.Assert;
import org.junit.Test;

public class BalanceAmountHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);

    @Test
    public void balanceAmountHandler_usingStrategy_ok() {
        AmountHandler amountHandler = new BalanceAmountHandler();
        int actual = amountHandler.changeAmount(TEST_FRUIT, 10);
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }
}
