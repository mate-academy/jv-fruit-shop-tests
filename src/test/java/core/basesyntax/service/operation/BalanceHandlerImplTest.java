package core.basesyntax.service.operation;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static Handler balanceHandler;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceHandlerImpl();
    }

    @Test
    public void changeAmount_Balance_Ok() {
        expected = 100;
        actual = balanceHandler.changeAmount(new FruitRecord("b",
                new Fruit("banana"), 100));
        Assert.assertEquals(expected, actual);
        expected = 100;
        actual = balanceHandler.changeAmount(new FruitRecord("b",
                new Fruit("apple"), 100));
        Assert.assertEquals(expected, actual);
    }
}
