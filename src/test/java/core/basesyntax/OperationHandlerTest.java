package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.handler.DecreaseOperationHandler;
import core.basesyntax.handler.IncreaseOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static OperationHandler operationHandlerDecrease;
    private static OperationHandler operationHandlerIncrease;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerDecrease = new DecreaseOperationHandler();
        operationHandlerIncrease = new IncreaseOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
    }

    @Test
    public void apply_DecreaseOperationHandler_isOk() {
        Storage.fruitStorage.put(apple, 20);
        Storage.fruitStorage.put(banana, 10);
        Assert.assertEquals(10, operationHandlerDecrease.apply(new FruitRecordDto(
                                            apple, 10, OperationType.PURCHASE)));
        Assert.assertEquals(0, operationHandlerDecrease.apply(new FruitRecordDto(
                                            banana, 10, OperationType.PURCHASE)));
    }

    @Test
    public void apply_IncreaseOperationHandler_isOk() {
        Storage.fruitStorage.put(banana, 10);
        Assert.assertEquals(20, operationHandlerIncrease.apply(new FruitRecordDto(
                                                apple, 20, OperationType.SUPPLY)));
        Assert.assertEquals(15, operationHandlerIncrease.apply(new FruitRecordDto(
                                                banana, 5, OperationType.RETURN)));
    }

    @Test (expected = RuntimeException.class)
    public void apply_DecreaseOperationHandler_NotOk() {
        Storage.fruitStorage.put(banana, 15);
        operationHandlerDecrease.apply(new FruitRecordDto(banana, 20, OperationType.PURCHASE));
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
