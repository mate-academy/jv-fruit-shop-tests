package core.basesyntax.shop;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.strategy.BalanceOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitsCount.clear();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @Test
    public void apply_emptyStorage_Ok() {
        balanceOperationHandler.apply(new FruitTransaction("b", "banana", 38));
        Integer expected = 38;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorage_Ok() {
        Storage.fruitsCount.put(banana, 12);
        balanceOperationHandler.apply(new FruitTransaction("b", "banana", 38));
        Integer expected = 38;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitName_Ok() {
        Storage.fruitsCount.put(banana, 12);
        balanceOperationHandler.apply(new FruitTransaction("b", "notCorrect", 38));
        Integer expected = 12;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        balanceOperationHandler.apply(new FruitTransaction("r", "banana", 38));
        Integer expected = 38;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }
}
