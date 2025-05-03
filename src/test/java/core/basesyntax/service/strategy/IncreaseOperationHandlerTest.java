package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.IncorrectAdditionalGoodsException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseOperationHandlerTest {
    private static OperationHandler increaseHandler;
    private static FruitRecordDto correctFruitForIncrease;
    private static FruitRecordDto incorrectFruitForIncrease;

    @BeforeClass
    public static void beforeClass() {
        increaseHandler = new IncreaseOperationHandler();
        correctFruitForIncrease = new FruitRecordDto(OperationType.BALANCE,
                new Fruit("fruit"), 21);
        incorrectFruitForIncrease = new FruitRecordDto(OperationType.SUPPLY,
                new Fruit("fruit"), -21);
    }

    @Before
    public void setUp() {
        FruitStorage.storage.clear();
    }

    @Test
    public void operationHandlerIncrease_Ok() {
        increaseHandler.apply(correctFruitForIncrease);
        int quantity = FruitStorage.storage.get(correctFruitForIncrease.getFruitName());
        assertEquals(quantity, correctFruitForIncrease.getQuantity());
    }

    @Test(expected = IncorrectAdditionalGoodsException.class)
    public void operationHandlerIncrease_NotOk() {
        increaseHandler.apply(incorrectFruitForIncrease);
    }
}
