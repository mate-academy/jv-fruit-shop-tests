package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.servise.FruitRecordDto;
import core.basesyntax.servise.Operation;
import core.basesyntax.servise.exception.InvalidValueOfAmountException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StrategySubtractionTest {
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");
    private Strategy subtraction;

    @Before
    public void setUp() {
        Storage.storageOfFruits.put(banana, 25);
        subtraction = new StrategySubtraction();
    }

    @Test
    public void changeBalance_SubtractionAmount_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.PURCHASE, banana, 25);
        int actual = subtraction.changeBalance(fruitRecordDto);
        int expected = 0;
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }

    @Test (expected = InvalidValueOfAmountException.class)
    public void changeBalance_SubtractionAmountLeseZero_NotOk() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.PURCHASE, banana, 35);
        subtraction.changeBalance(fruitRecordDto);
    }

    @Test (expected = InvalidValueOfAmountException.class)
    public void changeBalance_SubtractionByNotExistFruit_NotOk() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.PURCHASE, apple, 35);
        subtraction.changeBalance(fruitRecordDto);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storageOfFruits.clear();
    }
}
