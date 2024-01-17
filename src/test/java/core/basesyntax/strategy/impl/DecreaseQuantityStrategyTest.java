package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DecreaseQuantityStrategyTest {
    private static DecreaseQuantityStrategy strategy;

    @BeforeAll
    public static void setUp() {
        strategy = new DecreaseQuantityStrategy(new FruitDaoImpl());
    }

    @Test
    public void apply_validData_ok() {
        FruitStorage.getFruits().put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 3);
        strategy.apply(transaction);
        Map<String, Integer> actual = Map.of("banana", 7);
        Map<String, Integer> expected = FruitStorage.getFruits();
        assertEquals(actual, expected, "Decrease strategy is not working correctly!");
    }

    @Test
    public void apply_negativeQuantity_notOk() {
        FruitStorage.getFruits().put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 20);
        assertThrows(RuntimeException.class, () -> strategy.apply(transaction),
                "RuntimeException should be thrown if quantity is negative!");
    }

    @AfterEach
    public void afterEachTest() {
        FruitStorage.getFruits().clear();
    }
}
