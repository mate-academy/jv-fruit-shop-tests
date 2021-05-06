package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitOperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RemoveOperationHandlerTest {
    private FruitOperationHandler removeOperationHandler = new RemoveOperationHandler();

    @Before
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_CorrectFruitRecordDtoWithOperationPurchase_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto("p", "banana", 40);
        Fruit banana = new Fruit(fruitRecordDto.getFruitName());
        Storage.fruits.put(banana, 50);
        int expected = 10;
        int actual = removeOperationHandler.apply(fruitRecordDto);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_NotCorrectData() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto("p",
                "banana", 60);
        Fruit banana = new Fruit(fruitRecordDto.getFruitName());
        Storage.fruits.put(banana, 50);
        removeOperationHandler.apply(fruitRecordDto);
    }
}
