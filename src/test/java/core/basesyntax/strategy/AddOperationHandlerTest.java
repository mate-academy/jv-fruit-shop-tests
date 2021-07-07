package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private AddOperationHandler addOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
    }

    @Before
    public void setUp() {
        addOperationHandler = new AddOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        addOperationHandler.apply(null);
    }

    @Test
    public void apply_emptyStorage_Ok() {
        addOperationHandler.apply(new ShopOperation("s", "banana", 42));
        Integer expected = 42;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorage_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("s", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitName_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("s", "g&*FA7sf", 42));
        Integer expected = 8;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperation_Ok() {
        Storage.storage.put(banana, 8);
        addOperationHandler.apply(new ShopOperation("b", "banana", 42));
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

}

