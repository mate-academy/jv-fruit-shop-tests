package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AmountHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyAmountHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void supplyAmountHandler_usingStrategy_ok() {
        Storage.fruits.add(TEST_FRUIT);
        AmountHandler amountHandler = new SupplyAmountHandler();
        int actual = amountHandler.changeAmount(Storage.fruits.get(0), 10);
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }
}
