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

public class SupplyActivityHandlerTest {
    private static final int BANANA_BALANCE = 100;
    private static final int AMOUNT_SUPPLIED = 20;
    private static final int AMOUNT_LEFT = 120;
    private static ActivityHandler supplyActivityHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyActivityHandler = new SupplyActivityHandler();
    }

    @Test
    public void handle_successfulHandling_ok() {
        Fruit banana = new Fruit("banana");
        FruitStorage.fruitStorage.put(banana, BANANA_BALANCE);
        supplyActivityHandler.handle(new FruitTransaction(
                ActivityType.SUPPLY, banana, AMOUNT_SUPPLIED));
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(AMOUNT_LEFT, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
