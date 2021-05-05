package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.servise.FruitRecordDto;
import core.basesyntax.servise.Operation;
import org.junit.Before;
import org.junit.Test;

public class StrategyAddTest {
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");
    private Strategy addition;

    @Before
    public void setUp() {
        Storage.storageOfFruits.put(BANANA, 25);
        addition = new StrategyAdd();
    }

    @Test
    public void changeBalance_AddBalance_Ok() {
        FruitRecordDto firstTest = new FruitRecordDto(Operation.SUPPLY, BANANA, 25);
        int actual = addition.changeBalance(firstTest);
        int expected = Storage.storageOfFruits.get(BANANA);
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }

    @Test
    public void changeBalance_AddBalanceToNull_Ok() {
        FruitRecordDto firstTest = new FruitRecordDto(Operation.BALANCE, APPLE, 25);
        int actual = addition.changeBalance(firstTest);
        int expected = Storage.storageOfFruits.get(APPLE);
        assertEquals("Result was wrong! Expected: " + expected
                + " but was: " + actual, expected, actual);
    }
}
