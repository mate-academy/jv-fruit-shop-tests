package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.Test;

public class SupplyFruitCalculatorServiceTest {
    private FruitStrategy fruitStrategy = new FruitStrategy();

    @Test
    public void getFruitService_addValidSupply_Ok() {
        int expected = 35;
        int actual = fruitStrategy.getFruitService("s")
                .calculateFruits(15, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidSupply_Ok() {
        fruitStrategy.getFruitService("s")
                .calculateFruits(-15, 20);
    }
}
