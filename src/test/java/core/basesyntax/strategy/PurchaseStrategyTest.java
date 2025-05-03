package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseStrategyTest {
    private PurchaseStrategy purchaseStrategy;
    private Map<String, Integer> fruitCounts;

    @BeforeEach
    void setUp() {
        purchaseStrategy = new PurchaseStrategy();
        fruitCounts = new HashMap<>();
        fruitCounts.put("apple", 10);
        fruitCounts.put("banana", 20);

    }

    @Test
    void apply() {
        purchaseStrategy.apply(fruitCounts, "apple", 5);
        assertEquals(5, fruitCounts.get("apple"));
        purchaseStrategy.apply(fruitCounts, "banana", 10);
        assertEquals(10, fruitCounts.get("banana"));
    }

    @Test
    void applyNotEnough() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            purchaseStrategy.apply(fruitCounts, "banana", 25);
        });
        String expectedMessage = "Not enough " + "banana" + " in the store";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
