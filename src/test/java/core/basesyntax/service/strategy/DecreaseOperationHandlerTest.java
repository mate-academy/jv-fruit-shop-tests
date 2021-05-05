package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.IncorrectPurchaseRequestException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecreaseOperationHandlerTest {
    private static FruitRecordDto correctFruitForIncrease;
    private static FruitRecordDto incorrectFruitForIncrease;
    private static FruitRecordDto correctFruitForDecrease;
    private static FruitRecordDto incorrectFruitForDecrease;
    private static OperationHandler increaseHandler;
    private static OperationHandler decreaseHandler;
    private static final int ZERO = 0;

    @BeforeClass
    public static void beforeClass() {
        correctFruitForIncrease = new FruitRecordDto(OperationType.BALANCE,
                new Fruit("fruit"), 21);
        incorrectFruitForIncrease = new FruitRecordDto(OperationType.BALANCE,
                new Fruit("not_fruit"), 22);
        correctFruitForDecrease = new FruitRecordDto(OperationType.PURCHASE,
                new Fruit("fruit"), 21);
        incorrectFruitForDecrease = new FruitRecordDto(OperationType.PURCHASE,
                new Fruit("not_fruit"), 23);
        increaseHandler = new IncreaseOperationHandler();
        decreaseHandler = new DecreaseOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.storage.clear();
    }

    @Test
    public void operationHandlerDecrease_Ok() {
        increaseHandler.apply(correctFruitForIncrease);
        decreaseHandler.apply(correctFruitForDecrease);
        int actual = FruitStorage.storage.get(correctFruitForDecrease.getFruitName());
        assertEquals(actual, ZERO);
    }

    @Test(expected = IncorrectPurchaseRequestException.class)
    public void operationHandlerDecrease_NotOk() {
        increaseHandler.apply(incorrectFruitForIncrease);
        decreaseHandler.apply(incorrectFruitForDecrease);
    }
}
