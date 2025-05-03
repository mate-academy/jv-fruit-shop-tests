package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceStrategyTest {
    private Map<String, Integer> fruitCounts;
    private BalanceStrategy balanceStrategy;

    @BeforeEach
    void setUp() {
        balanceStrategy = new BalanceStrategy();
        fruitCounts = new HashMap<>();
        fruitCounts.put("apple", 10);
        fruitCounts.put("banana", 20);
    }

    @Test
    void apply() {
        balanceStrategy.apply(fruitCounts, "banana", 5);
        assertEquals(25, fruitCounts.get("banana"));
        balanceStrategy.apply(fruitCounts, "apple", 5);
        assertEquals(15, fruitCounts.get("apple"));
    }
}
