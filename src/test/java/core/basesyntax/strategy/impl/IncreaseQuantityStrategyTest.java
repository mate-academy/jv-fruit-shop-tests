package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IncreaseQuantityStrategyTest {
    private static IncreaseQuantityStrategy strategy;

    @BeforeAll
    public static void setUp() {
        strategy = new IncreaseQuantityStrategy(new FruitDaoImpl());
    }

    @Test
    void increase_OK() {
        FruitStorage.getFruits().put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 3);
        strategy.apply(transaction);
        Map<String, Integer> actual = Map.of("banana", 8);
        Map<String, Integer> expected = FruitStorage.getFruits();
        assertEquals(expected, actual, "Increase strategy is not working correctly!");
    }

    @Test
    void increaseQuantity_ZeroValue_OK() {
        FruitStorage.getFruits().put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 0);
        strategy.apply(transaction);

        Map<String, Integer> actual = Map.of("banana", 5);
        Map<String, Integer> expected = FruitStorage.getFruits();
        assertEquals(expected, actual, "Increase strategy is not working correctly!");
    }

    @Test
    void increaseQuantity_NegativeValue_Exception() {
        FruitStorage.getFruits().put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", -2);

        assertThrows(IllegalArgumentException.class, () -> strategy.apply(transaction),
                "IllegalArgumentException should be thrown for negative quantity!");
    }
}
