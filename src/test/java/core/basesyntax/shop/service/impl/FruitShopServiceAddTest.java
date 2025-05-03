package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDao;
import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.FruitShopService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceAddTest {
    private static FruitShopDao fruitShopDao;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        fruitShopService = new FruitShopServiceAdd(fruitShopDao);
    }

    @AfterClass
    public static void clearAfterTest() {
        getAll().clear();
    }

    @Test
    public void apply_addFruitsToStrorage_ok() {
        Fruit banana = new Fruit("banana");
        fruitShopService.apply(banana, 10);
        int actual = getAll().get(banana);
        Assert.assertEquals(10, actual);
    }
}
