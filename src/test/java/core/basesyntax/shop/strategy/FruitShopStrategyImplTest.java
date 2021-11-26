package core.basesyntax.shop.strategy;

import core.basesyntax.shop.service.impl.FruitShopServiceAdd;
import core.basesyntax.shop.service.impl.FruitShopServiceSubtract;
import org.junit.Assert;
import org.junit.Test;

public class FruitShopStrategyImplTest {
    @Test
    public void chooseStrategy_findStrategyOfNecessaryOperation_ok() {
        FruitShopStrategy fruitShopStrategy = new FruitShopStrategyImpl();
        Assert.assertEquals(fruitShopStrategy.chooseStrategy("b"),
                FruitShopServiceAdd.class);
        Assert.assertEquals(fruitShopStrategy.chooseStrategy("s"),
                FruitShopServiceAdd.class);
        Assert.assertEquals(fruitShopStrategy.chooseStrategy("r"),
                FruitShopServiceAdd.class);
        Assert.assertEquals(fruitShopStrategy.chooseStrategy("p"),
                FruitShopServiceSubtract.class);
    }
}
