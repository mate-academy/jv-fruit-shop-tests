package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.strategy.FruitStrategy;
import org.junit.Test;

public class PurchaseFruitCalculatorServiceTest {
    private FruitStrategy fruitStrategy = new FruitStrategy();

    @Test
    public void getFruitService_addValidPurchase_Ok() {
        int expected = 25;
        int actual = fruitStrategy.getFruitService("p")
                .calculateFruits(45, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidPurchase_Ok() {
        fruitStrategy.getFruitService("p")
                .calculateFruits(-45, -20);
    }
}
