package core.basesyntax.strategy;

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
}
