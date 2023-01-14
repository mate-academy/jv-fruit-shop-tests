package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.Test;

public class ReturnFruitCalculatorServiceTest {
    private FruitStrategy fruitStrategy = new FruitStrategy();

    @Test
    public void getFruitService_addValidReturn_Ok() {
        int expected = 65;
        int actual = fruitStrategy.getFruitService("r")
                .calculateFruits(45, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidReturn_Ok() {
        fruitStrategy.getFruitService("r")
                .calculateFruits(45, -20);
    }
}
