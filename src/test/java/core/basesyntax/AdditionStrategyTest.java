package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.AdditionStrategy;
import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdditionStrategyTest {
    private static OperationStrategy operationStrategy;
    private static FruitRecordDto firstTest;
    private static FruitRecordDto secondTest;

    @BeforeClass
    public static void setUp() {
        firstTest = new FruitRecordDto(
                Operation.SUPPLY, new Fruit("banana"), 100);
        secondTest = new FruitRecordDto(Operation.BALANCE, new Fruit("apple"), 50);
        operationStrategy = new AdditionStrategy();
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }

    @Test
    public void additionStategy_ok() {
        operationStrategy.apply(firstTest);
        operationStrategy.apply(firstTest);
        operationStrategy.apply(secondTest);
        Integer bananaQuantity = Storage.fruits.get(firstTest.getFruit());
        Integer appleQuantity = Storage.fruits.get(secondTest.getFruit());
        Assert.assertEquals(200, bananaQuantity.intValue());
        Assert.assertEquals(50, appleQuantity.intValue());
    }
}
