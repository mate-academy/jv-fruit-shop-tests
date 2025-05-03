package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReturnStrategyTest {
    private final Strategy strategy = new ReturnStrategy();

    @Test
    void calculateFruitQuantity_isOk() {
        Integer num = 100;
        Integer num2 = 20;
        Integer expected = num + num2;
        assertEquals(expected, strategy.calculateFruitQuantity(100, 20));
    }
}
