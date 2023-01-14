package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import org.junit.Test;

public class FruitStrategyTest {
    private FruitStrategy fruitStrategy = new FruitStrategy();

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidOperation_notOk() {
        fruitStrategy.getFruitService("k");
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addNullOperation_notOk() {
        fruitStrategy.getFruitService(null);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addEmptyOperation_notOk() {
        fruitStrategy.getFruitService("");
    }

    @Test
    public void getFruitService_addValidBalance_Ok() {
        int expected = 20;
        int actual = fruitStrategy.getFruitService("b")
                .calculateFruits(10, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test
    public void getFruitService_addValidPurchase_Ok() {
        int expected = 25;
        int actual = fruitStrategy.getFruitService("p")
                .calculateFruits(45, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test
    public void getFruitService_addValidReturn_Ok() {
        int expected = 65;
        int actual = fruitStrategy.getFruitService("r")
                .calculateFruits(45, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test
    public void getFruitService_addValidSupply_Ok() {
        int expected = 35;
        int actual = fruitStrategy.getFruitService("s")
                .calculateFruits(15, 20);
        boolean isEqual = expected == actual;
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidBalance_Ok() {
        fruitStrategy.getFruitService("b")
                .calculateFruits(10, -20);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidPurchase_Ok() {
        fruitStrategy.getFruitService("p")
                .calculateFruits(-45, -20);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidReturn_Ok() {
        fruitStrategy.getFruitService("r")
                .calculateFruits(45, -20);
    }

    @Test(expected = FruitShopException.class)
    public void getFruitService_addInvalidSupply_Ok() {
        fruitStrategy.getFruitService("s")
                .calculateFruits(-15, 20);
    }
}
