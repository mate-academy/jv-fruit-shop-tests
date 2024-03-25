package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BalanceStrategyTest {
    private final Strategy strategy = new BalanceStrategy();

    @Test
    void calculateFruitQuantity_isOk() {
        Integer num = 10;
        Integer num2 = 5;
        Integer expected = num2;
        assertEquals(expected, strategy.calculateFruitQuantity(num, num2));
    }
}
