package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnFruitCalculatorServiceTest {
    private static FruitStrategy fruitStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitStrategy = new FruitStrategy();
    }

    @Test
    public void getFruitService_addValidReturn_ok() {
        int expected = 65;
        int actual = fruitStrategy.getFruitService("r")
                .calculateFruits(45, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidReturn_ok() {
        fruitStrategy.getFruitService("r")
                .calculateFruits(45, -20);
    }
}
