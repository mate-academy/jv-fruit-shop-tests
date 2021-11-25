package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDao;
import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.FruitShopService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class FruitShopServiceAddTest {
    @AfterClass
    public static void clearAfterTest() {
        getAll().clear();
    }

    @Test
    public void apply_addFruitsToStrorage_ok() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        FruitShopService fruitShopService = new FruitShopServiceAdd(fruitShopDao);
        Fruit banana = new Fruit("banana");
        fruitShopService.apply(banana, 10);
        int actual = getAll().get(banana);
        Assert.assertEquals(10, actual);
    }
}
