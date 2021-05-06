package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitOperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddOperationHandlerTest {
    private FruitOperationHandler fruitRecordDtoParser;
    private FruitRecordDto fruitRecordDto;

    @Before
    public void setUp() {
        Storage.getFruits().clear();
        fruitRecordDtoParser = new AddOperationHandler();
    }

    @Test
    public void apply_CorrectFruitRecordDtoWithOperationSupply_Ok() {
        fruitRecordDto = new FruitRecordDto("s", "apple", 20);
        Fruit fruit = new Fruit("apple");
        Storage.fruits.put(fruit, 24);
        int expected = 44;
        int actual = fruitRecordDtoParser.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_CorrectFruitRecordDtoWithOperationReturn_Ok() {
        fruitRecordDto = new FruitRecordDto("r", "mango", 13);
        Fruit fruit = new Fruit("mango");
        Storage.fruits.put(fruit, 24);
        int expected = 37;
        int actual = fruitRecordDtoParser.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }
}
