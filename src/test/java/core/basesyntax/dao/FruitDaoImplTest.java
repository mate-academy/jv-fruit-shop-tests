package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @Test(expected = RuntimeException.class)
    public void update_purchaseNotEnough_notOk() {
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 1);
        Integer amountToPurchase = 2;
        fruitDao.update(fruit, -amountToPurchase);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
