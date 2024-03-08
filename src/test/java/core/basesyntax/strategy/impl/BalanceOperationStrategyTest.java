package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationStrategyTest {
    private static BalanceOperationStrategy strategy;

    @BeforeAll
    public static void setUp() {
        strategy = new BalanceOperationStrategy(new FruitDaoImpl());
    }

    @Test
    public void apply_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE,
                "banana", 10);
        strategy.apply(transaction);
        Map<String, Integer> actual = Map.of(transaction.getFruitName(),
                transaction.getQuantity());
        Map<String, Integer> expected = FruitStorage.getFruits();
        assertEquals(actual, expected, "Balance strategy is not working correctly!");
    }

    @AfterEach
    public void afterEachTest() {
        FruitStorage.getFruits().clear();
    }
}
