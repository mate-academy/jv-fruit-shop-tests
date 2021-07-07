package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @Test
    public void apply_emptyStorage_Ok() {
        balanceOperationHandler.apply(new ShopOperation("b", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorage_Ok() {
        Storage.storage.put(banana, 8);
        balanceOperationHandler.apply(new ShopOperation("b", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitName_Ok() {
        Storage.storage.put(banana, 8);
        balanceOperationHandler.apply(new ShopOperation("b", "g&*FA7sf", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperation_Ok() {
        Storage.storage.put(banana, 8);
        balanceOperationHandler.apply(new ShopOperation("r", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

}

