package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDao;
import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.FruitShopService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceSubtractTest {
    private static FruitShopDao fruitShopDao;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        fruitShopService = new FruitShopServiceSubtract(fruitShopDao);
    }

    @Before
    public void fillStorageBeforeTest() {
        Fruit apple = new Fruit("apple");
        getAll().put(apple, 15);
    }

    @After
    public void clearAfterTest() {
        getAll().clear();
    }

    @Test
    public void apply_removeFruitsFromStrorage_ok() {
        Fruit apple = new Fruit("apple");
        fruitShopService.apply(apple, 10);
        int actual = getAll().get(apple);
        Assert.assertEquals(5, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_removeFruitsFromStorageMoreThanPossible_notOk() {
        Fruit apple = new Fruit("apple");
        fruitShopService.apply(apple, 20);
    }
}
