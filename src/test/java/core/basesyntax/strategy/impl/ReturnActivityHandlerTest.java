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

public class ReturnActivityHandlerTest {
    private static final int BANANA_BALANCE = 100;
    private static final int AMOUNT_RETURNED = 20;
    private static final int AMOUNT_LEFT = 120;
    private static ActivityHandler returneActivityHandler;

    @BeforeClass
    public static void beforeClass() {
        returneActivityHandler = new ReturnActivityHandler();
    }

    @Test
    public void handle_successfulHandling_ok() {
        Fruit banana = new Fruit("banana");
        FruitStorage.fruitStorage.put(banana, BANANA_BALANCE);
        returneActivityHandler.handle(new FruitTransaction(
                ActivityType.RETURN, banana, AMOUNT_RETURNED));
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(AMOUNT_LEFT, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
