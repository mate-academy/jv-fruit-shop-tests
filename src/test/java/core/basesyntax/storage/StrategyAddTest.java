package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.servise.FruitRecordDto;
import core.basesyntax.servise.Operation;
import org.junit.Before;
import org.junit.Test;

public class StrategyAddTest {
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");
    private Strategy addition;

    @Before
    public void setUp() {
        Storage.storageOfFruits.put(banana, 25);
        addition = new StrategyAdd();
    }

    @Test
    public void changeBalance_AddBalance_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.SUPPLY, banana, 25);
        int actual = addition.changeBalance(fruitRecordDto);
        int expected = Storage.storageOfFruits.get(banana);
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }

    @Test
    public void changeBalance_AddBalanceToNull_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.BALANCE, apple, 25);
        int actual = addition.changeBalance(fruitRecordDto);
        int expected = Storage.storageOfFruits.get(apple);
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }
}
