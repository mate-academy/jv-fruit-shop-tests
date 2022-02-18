package core.basesyntax.shop;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.strategy.AddOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static AddOperationHandler addOperationHandler;
    private Fruit banana = new Fruit("banana");

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitsCount.clear();
        addOperationHandler = new AddOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        addOperationHandler.apply(null);
    }

    @Test
    public void apply_emptyStorageSupplyOperation_Ok() {
        addOperationHandler.apply(new FruitTransaction("s", "banana", 25));
        Integer expected = 25;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorageSupplyOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("s", "banana", 38));
        Integer expected = 50;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitNameSupplyOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("s", "notCorrect", 38));
        Integer expected = 12;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperationSupplyOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("b", "banana", 38));
        Integer expected = 50;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_emptyStorageReturnOperation_Ok() {
        addOperationHandler.apply(new FruitTransaction("r", "banana", 25));
        Integer expected = 25;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_nonEmptyStorageReturnOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("r", "banana", 38));
        Integer expected = 50;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectFruitNameReturnOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("r", "notCorrect", 38));
        Integer expected = 12;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_incorrectOperationReturnOperation_Ok() {
        Storage.fruitsCount.put(banana, 12);
        addOperationHandler.apply(new FruitTransaction("p", "banana", 38));
        Integer expected = 50;
        Integer actual = Storage.fruitsCount.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }
}
