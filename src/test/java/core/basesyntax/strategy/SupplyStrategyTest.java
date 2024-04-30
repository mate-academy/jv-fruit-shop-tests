package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private Map<String, Integer> fruitCounts;
    private SupplyStrategy supplyStrategy;

    @BeforeEach
    void setUp() {
        supplyStrategy = new SupplyStrategy();
        fruitCounts = new HashMap<>();
        fruitCounts.put("banana", 20);
        fruitCounts.put("apple", 10);

    }

    @Test
    void apply() {
        supplyStrategy.apply(fruitCounts, "banana", 5);
        assertEquals(25, fruitCounts.get("banana"));
        supplyStrategy.apply(fruitCounts, "apple", 5);
        assertEquals(15, fruitCounts.get("apple"));
    }
}
