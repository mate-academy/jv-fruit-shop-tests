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
    private static FruitRecordDto supplyTest;
    private static FruitRecordDto balanceTest;

    @BeforeClass
    public static void setUp() {
        supplyTest = new FruitRecordDto(
                Operation.SUPPLY, new Fruit("banana"), 100);
        balanceTest = new FruitRecordDto(Operation.BALANCE, new Fruit("apple"), 50);
        operationStrategy = new AdditionStrategy();
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }

    @Test
    public void additionStrategy_ok() {
        operationStrategy.apply(supplyTest);
        operationStrategy.apply(supplyTest);
        operationStrategy.apply(balanceTest);
        Integer bananaQuantity = Storage.fruits.get(supplyTest.getFruit());
        Integer appleQuantity = Storage.fruits.get(balanceTest.getFruit());
        Assert.assertEquals(200, bananaQuantity.intValue());
        Assert.assertEquals(50, appleQuantity.intValue());
    }
}
