package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoMapImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.AddToBalance;
import core.basesyntax.service.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddToBalanceTest {
    private static FruitShopDao fruitShopDao;
    private static OperationHandler addToBalance;

    @BeforeClass
    public static void setFruitShopDao() {
        fruitShopDao = new FruitShopDaoMapImpl();
        addToBalance = new AddToBalance(fruitShopDao);
    }

    @Test
    public void updateBalance_FruitIsAlreadyInDb_isOk() {
        fruitShopDao.add(new Fruit("orange", 100));
        Fruit fruit = new Fruit("orange", 10);
        long updatedBalance = addToBalance.updateBalance(fruit);
        assertEquals(110, updatedBalance);
    }

    @Test
    public void updateBalance_NoFruitInDb_isOk() {
        Fruit fruit = new Fruit("banana", 100);
        long updatedBalance = addToBalance.updateBalance(fruit);
        assertEquals(100, updatedBalance);
    }
}
