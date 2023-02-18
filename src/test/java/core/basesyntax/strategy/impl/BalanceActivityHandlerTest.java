package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ActivityHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceActivityHandlerTest {
    private static final int BANANA_BALANCE = 100;
    private static ActivityHandler balanceActivityHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceActivityHandler = new BalanceActivityHandler();
    }

    @Test
    public void handle_successfulHandling_ok() {
        Fruit banana = new Fruit("banana");
        FruitStorage.fruitStorage.put(banana, BANANA_BALANCE);
        balanceActivityHandler.handle(new FruitTransaction(
                ActivityType.BALANCE, banana, BANANA_BALANCE));
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(BANANA_BALANCE, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
