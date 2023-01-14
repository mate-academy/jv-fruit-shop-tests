package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.Test;

public class BalanceFruitCalculatorServiceTest {
    private FruitStrategy fruitStrategy = new FruitStrategy();

    @Test
    public void getFruitService_addValidBalance_Ok() {
        int expected = 20;
        int actual = fruitStrategy.getFruitService("b")
                .calculateFruits(10, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidBalance_Ok() {
        fruitStrategy.getFruitService("b")
                .calculateFruits(10, -20);
    }
}
