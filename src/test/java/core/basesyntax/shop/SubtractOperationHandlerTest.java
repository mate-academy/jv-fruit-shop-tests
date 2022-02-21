package core.basesyntax.shop;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.strategy.SubtractOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    private static SubtractOperationHandler subtractOperationHandler;
    private Fruit banana = new Fruit("banana");

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }

    @Before
    public void setUp() {
        Storage.fruitsCount.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        subtractOperationHandler = new SubtractOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        subtractOperationHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_emptyStorage_NotOk() {
        subtractOperationHandler.apply(new FruitTransaction("p", "banana", 38));
    }

    @Test
    public void apply_nonEmptyStorage_Ok() {
        Storage.fruitsCount.put(banana, 50);
        subtractOperationHandler.apply(new FruitTransaction("p", "banana", 38));
        Integer expected = 12;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_notEnoughProduct_Storage_NotOk() {
        Storage.fruitsCount.put(banana, 20);
        subtractOperationHandler.apply(new FruitTransaction("p", "banana", 30));

    }

    @Test
    public void apply_incorrectOperation_Ok() {
        Storage.fruitsCount.put(banana, 50);
        subtractOperationHandler.apply(new FruitTransaction("r", "banana", 12));
        Integer expected = 38;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }
}
