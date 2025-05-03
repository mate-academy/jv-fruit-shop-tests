package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoMapImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.SubtractFromBalance;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractFromBalanceTest {
    private static FruitShopDao fruitShopDao;
    private static OperationHandler subtractFromBalance;

    @BeforeClass
    public static void setFruitShopDao() {
        fruitShopDao = new FruitShopDaoMapImpl();
        subtractFromBalance = new SubtractFromBalance(fruitShopDao);
    }

    @Test
    public void updateBalance_FruitIsInDb_isOk() {
        fruitShopDao.add(new Fruit("apple", 100));
        Fruit fruit = new Fruit("apple", 50);
        long updatedBalance = subtractFromBalance.updateBalance(fruit);
        assertEquals(50, updatedBalance);
    }

    @Test(expected = RuntimeException.class)
    public void updateBalance_NotEnoughFruits_notOk() {
        fruitShopDao.add(new Fruit("banana", 100));
        Fruit fruit = new Fruit("banana", 200);
        subtractFromBalance.updateBalance(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void updateBalance_FruitsIsNotInDb_notOk() {
        Fruit fruit = new Fruit("orange", 20);
        subtractFromBalance.updateBalance(fruit);
    }
}
