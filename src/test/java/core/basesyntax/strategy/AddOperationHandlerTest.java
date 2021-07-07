package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static AddOperationHandler addOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
        addOperationHandler = new AddOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        addOperationHandler.apply(null);
    }

    @Test
    public void apply_emptyStorageSupplyOperation_Ok() {
        addOperationHandler.apply(new ShopOperation("s", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorageSupplyOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("s", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitNameSupplyOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("s", "g&*FA7sf", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperationSupplyOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("b", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_emptyStorageReturnOperation_Ok() {
        addOperationHandler.apply(new ShopOperation("r", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorageReturnOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("r", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitNameReturnOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("r", "g&*FA7sf", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperationReturnOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("p", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

}

