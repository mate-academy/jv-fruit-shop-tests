package core.basesyntax.strategy;

import static core.basesyntax.TestConstants.FRUIT_QUANTITY;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private final Strategy strategy = new SupplyStrategy();

    @Test
    void calculateFruitQuantity() {
        Integer supplyQuantity = 20;
        Integer expected = FRUIT_QUANTITY + supplyQuantity;
        Assertions.assertEquals(expected, strategy.calculateFruitQuantity(
                FRUIT_QUANTITY, supplyQuantity));
    }
}
