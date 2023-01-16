package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyFruitCalculatorServiceTest {
    private static FruitStrategy fruitStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitStrategy = new FruitStrategy();
    }

    @Test
    public void getFruitService_addValidSupply_ok() {
        int expected = 35;
        int actual = fruitStrategy.getFruitService("s")
                .calculateFruits(15, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidSupply_ok() {
        fruitStrategy.getFruitService("s")
                .calculateFruits(-15, 20);
    }
}
