package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceFruitCalculatorServiceTest {
    private static FruitStrategy fruitStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitStrategy = new FruitStrategy();
    }

    @Test
    public void getFruitService_addValidBalance_ok() {
        int expected = 20;
        int actual = fruitStrategy.getFruitService("b")
                .calculateFruits(10, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidBalance_ok() {
        fruitStrategy.getFruitService("b")
                .calculateFruits(10, -20);
    }
}
