package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    private SubtractOperationHandler subtractOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Before
    public void setUp() {
        subtractOperationHandler = new SubtractOperationHandler();
        Storage.storage.clear();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        subtractOperationHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_emptyStorage_NotOk() {
        subtractOperationHandler.apply(new ShopOperation("p", "banana", 42));
    }

    @Test
    public void apply_nonEmptyStorage_Ok() {
        Storage.storage.put(banana, 50);
        subtractOperationHandler.apply(new ShopOperation("p", "banana", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_notEnoughProduct_Storage_NotOk() {
        Storage.storage.put(banana, 20);
        subtractOperationHandler.apply(new ShopOperation("p", "banana", 30));

    }

    @Test
    public void apply_incorrectFruitName_Ok() {
        Storage.storage.put(banana, 8);
        subtractOperationHandler.apply(new ShopOperation("s", "gt3g3qw3f4", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperation_Ok() {
        Storage.storage.put(banana, 50);
        subtractOperationHandler.apply(new ShopOperation("r", "banana", 8));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

}

