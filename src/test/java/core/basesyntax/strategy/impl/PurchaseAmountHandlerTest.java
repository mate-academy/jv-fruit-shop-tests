package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AmountHandler;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseAmountHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);

    @Test
    public void purchaseAmountHandler_usingStrategy_ok() {
        AmountHandler amountHandler = new PurchaseAmountHandler();
        int actual = amountHandler.changeAmount(TEST_FRUIT, 10);
        int expected = 15;
        Assert.assertEquals(expected, actual);
    }
}
