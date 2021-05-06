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
    private static FruitRecordDto firstTest;
    private static FruitRecordDto secondTest;

    @BeforeClass
    public static void setUp() {
        firstTest = new FruitRecordDto(
                Operation.PURCHASE, new Fruit("banana"), 100);
        secondTest = new FruitRecordDto(Operation.PURCHASE, new Fruit("apple"), 50);
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
        operationStrategy.apply(firstTest);
        operationStrategy.apply(firstTest);
        operationStrategy.apply(secondTest);
        Integer bananaQuantity = Storage.fruits.get(firstTest.getFruit());
        Integer appleQuantity = Storage.fruits.get(secondTest.getFruit());
        Assert.assertEquals(600, bananaQuantity.intValue());
        Assert.assertEquals(450, appleQuantity.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void noSuchFruit() {
        operationStrategy.apply(new FruitRecordDto(
                Operation.PURCHASE, new Fruit("orange"), 40));
        operationStrategy.apply(firstTest);
        operationStrategy.apply(secondTest);
    }

    @Test(expected = RuntimeException.class)
    public void noSoMuchFruits() {
        Storage.fruits.put(new Fruit("orange"), 50);
        Storage.fruits.put(new Fruit("cherry"), 20);
        operationStrategy.apply(new FruitRecordDto(
                Operation.PURCHASE, new Fruit("orange"), 100));
        operationStrategy.apply(new FruitRecordDto(
                Operation.PURCHASE, new Fruit("cherry"), 50));
    }
}
