package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDao;
import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.FruitShopService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceSubtractTest {
    @Before
    public void fillStorageBeforeTest() {
        Fruit apple = new Fruit("apple");
        getAll().put(apple, 15);
    }

    @Test
    public void apply_removeFruitsFromStrorage_ok() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        FruitShopService fruitShopService = new FruitShopServiceSubtract(fruitShopDao);
        Fruit apple = new Fruit("apple");
        fruitShopService.apply(apple, 10);
        int actual = getAll().get(apple);
        Assert.assertEquals(5, actual);
    }

    @Test
    public void apply_removeFruitsFromStorageMoreThanPossible_notOk() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        FruitShopService fruitShopService = new FruitShopServiceSubtract(fruitShopDao);
        Fruit apple = new Fruit("apple");
        try {
            fruitShopService.apply(apple, 20);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Runtime exception was expected");
    }

    @After
    public void clearAfterTest() {
        getAll().clear();
    }
}
