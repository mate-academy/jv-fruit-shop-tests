package core.basesyntax.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private final Strategy strategy = new SupplyStrategy();

    @Test
    void calculateFruitQuantity() {
        Integer num = 100;
        Integer num2 = 20;
        Integer expected = num + num2;
        Assertions.assertEquals(expected, strategy.calculateFruitQuantity(num, num2));
    }
}
