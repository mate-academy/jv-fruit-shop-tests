package core.basesyntax.service.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseOperationHandlerImplTest {
    private static IncreaseOperationHandlerImpl operationHandler;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new IncreaseOperationHandlerImpl();
        fruitRecordDto = new FruitRecordDto();
    }

    @Test
    public void getAmount_correctData_Ok() {
        Storage.fruitStorage.put(new Fruit("apple"), 150);
        fruitRecordDto.setFruit(new Fruit("apple"));
        fruitRecordDto.setAmount(13);
        fruitRecordDto.setOperationType(FruitRecordDto.OperationType.RETURN);
        int actual = operationHandler.getAmount(fruitRecordDto);
        int expected = 163;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getAmount_incorrectData_NotOk() {
        Storage.fruitStorage.put(new Fruit("apple"), 150);
        fruitRecordDto.setFruit(new Fruit("mango"));
        fruitRecordDto.setAmount(15);
        fruitRecordDto.setOperationType(FruitRecordDto.OperationType.RETURN);
        operationHandler.getAmount(fruitRecordDto);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
