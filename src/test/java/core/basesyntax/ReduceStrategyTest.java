package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReduceStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReduceStrategyTest {
    private static OperationStrategy operationStrategy;
    private static FruitRecordDto purchaseTest;

    @BeforeClass
    public static void setUp() {
        purchaseTest = new FruitRecordDto(
                Operation.PURCHASE, new Fruit("banana"), 100);
        operationStrategy = new ReduceStrategy();
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }

    @Test
    public void additionStrategy_ok() {
        Storage.fruits.put(new Fruit("banana"), 800);
        Storage.fruits.put(new Fruit("apple"), 500);
        operationStrategy.apply(purchaseTest);
        operationStrategy.apply(purchaseTest);
        Integer bananaQuantity = Storage.fruits.get(purchaseTest.getFruit());
        Assert.assertEquals(600, bananaQuantity.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void noSuchFruit() {
        operationStrategy.apply(new FruitRecordDto(
                Operation.PURCHASE, new Fruit("orange"), 40));
        operationStrategy.apply(purchaseTest);
    }

    @Test(expected = RuntimeException.class)
    public void noSoMuchFruits() {
        Storage.fruits.put(new Fruit("orange"), 50);
        operationStrategy.apply(new FruitRecordDto(
                Operation.PURCHASE, new Fruit("orange"), 100));
    }
}
