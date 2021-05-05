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
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");
    private Strategy subtraction;

    @Before
    public void setUp() {
        Storage.storageOfFruits.put(BANANA, 25);
        subtraction = new StrategySubtraction();
    }

    @Test
    public void changeBalance_SubtractionAmount_Ok() {
        FruitRecordDto firstTest = new FruitRecordDto(Operation.PURCHASE, BANANA, 25);
        int actual = subtraction.changeBalance(firstTest);
        int expected = 0;
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }

    @Test (expected = InvalidValueOfAmountException.class)
    public void changeBalance_SubtractionAmountLeseZero_NotOk() {
        FruitRecordDto firstTest = new FruitRecordDto(Operation.PURCHASE, BANANA, 35);
        subtraction.changeBalance(firstTest);
    }

    @Test (expected = InvalidValueOfAmountException.class)
    public void changeBalance_SubtractionByNotExistFruit_NotOk() {
        FruitRecordDto firstTest = new FruitRecordDto(Operation.PURCHASE, APPLE, 35);
        subtraction.changeBalance(firstTest);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storageOfFruits.clear();
    }
}
