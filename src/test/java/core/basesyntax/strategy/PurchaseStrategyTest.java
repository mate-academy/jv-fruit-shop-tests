package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PurchaseStrategyTest {
    private final Strategy strategy = new PurchaseStrategy();

    @Test
    void calculateFruitQuantity_isOk() {
        Integer num = 20;
        Integer num2 = 5;
        Integer expected = num - num2;
        assertEquals(expected, strategy.calculateFruitQuantity(num, num2));
    }
}
