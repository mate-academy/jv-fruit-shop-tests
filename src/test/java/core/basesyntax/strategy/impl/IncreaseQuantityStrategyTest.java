package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void apply_validData_ok() {
        FruitStorage.getFruits().put("banana", 6);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 4);
        strategy.apply(transaction);
        Map<String, Integer> actual = Map.of("banana", 10);
        Map<String, Integer> expected = FruitStorage.getFruits();
        assertEquals(actual, expected, "Increase strategy is not working correctly!");
    }
}
