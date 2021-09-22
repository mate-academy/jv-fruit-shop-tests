package core.basesyntax.service.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecreaseOperationHandlerImplTest {
    private static DecreaseOperationHandlerImpl operationHandler;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new DecreaseOperationHandlerImpl();
        fruitRecordDto = new FruitRecordDto();
        Storage.fruitStorage.put(new Fruit("apple"), 150);
    }

    @Test
    public void getAmount_correctData_Ok() {
        fruitRecordDto.setFruit(new Fruit("apple"));
        fruitRecordDto.setAmount(20);
        fruitRecordDto.setOperationType(FruitRecordDto.OperationType.PURCHASE);
        int actual = operationHandler.getAmount(fruitRecordDto);
        int expected = 130;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getAmount_incorrectData_NotOk() {
        fruitRecordDto.setFruit(new Fruit("mango"));
        fruitRecordDto.setAmount(20);
        fruitRecordDto.setOperationType(FruitRecordDto.OperationType.PURCHASE);
        operationHandler.getAmount(fruitRecordDto);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
