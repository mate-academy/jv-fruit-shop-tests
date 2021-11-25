package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.ShopDataParser;
import core.basesyntax.shop.strategy.FruitShopStrategyImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ShopDataParserImplTest {
    @AfterClass
    public static void clearAfterTest() {
        getAll().clear();
    }

    @Test
    public void distribute_ParsingDataToStorage_ok() {
        String table = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "b,durian,8\n"
                + "p,apple,20\n"
                + "r,apple,10\n"
                + "p,banana,5\n"
                + "s,banana,50\n"
                + "b,pear,50\n"
                + "p,pear,10";
        ShopDataParser shopDataParser = new ShopDataParserImpl(
                new FruitShopStrategyImpl(), new FruitShopDaoImpl());
        shopDataParser.distribute(table);
        Assert.assertEquals(getAll().get(new Fruit("banana")), (Integer) 152);
        Assert.assertEquals(getAll().get(new Fruit("pear")), (Integer) 40);
        Assert.assertEquals(getAll().get(new Fruit("apple")), (Integer) 90);
        Assert.assertEquals(getAll().get(new Fruit("durian")), (Integer) 8);
    }
}
