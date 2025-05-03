package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnStrategyTest {
    private Map<String, Integer> fruitCounts;
    private ReturnStrategy returnStrategy;

    @BeforeEach
    void setUp() {
        returnStrategy = new ReturnStrategy();
        fruitCounts = new HashMap<>();
        fruitCounts.put("apple", 10);
        fruitCounts.put("banana", 20);
    }

    @Test
    void apply() {
        returnStrategy.apply(fruitCounts, "apple", 5);
        assertEquals(15, fruitCounts.get("apple"));
        returnStrategy.apply(fruitCounts, "banana", 10);
        assertEquals(30, fruitCounts.get("banana"));
    }
}
