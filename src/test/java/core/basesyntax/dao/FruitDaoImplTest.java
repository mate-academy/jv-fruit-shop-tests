package core.basesyntax.dao;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit BANANA = new Fruit("Banana");
    private static final Fruit APPLE = new Fruit("Apple");
    private static final Fruit ORANGE = new Fruit("Orange");
    private static FruitDao fruitDao = new FruitDaoImpl();
    private static Map<Fruit, Integer> testStorage = new HashMap<>();

    @Before
    public void setUp() {
        testStorage.put(BANANA, 100);
        testStorage.put(APPLE, 150);
        testStorage.put(ORANGE, 200);
        fruitDao.update(BANANA, 100);
        fruitDao.update(APPLE, 150);
        fruitDao.update(ORANGE, 200);
    }

    @Test
    public void updateData_ok() {
        Assert.assertEquals(testStorage, Storage.fruits);
    }

    @Test
    public void getRemainFruit_ok() {
        Assert.assertEquals(Integer.valueOf(100),
                fruitDao.getQuantity(BANANA));
        Assert.assertEquals(Integer.valueOf(150),
                fruitDao.getQuantity(APPLE));
        Assert.assertEquals(Integer.valueOf(200),
                fruitDao.getQuantity(ORANGE));
    }

    @Test
    public void getStorage_ok() {
        Assert.assertEquals(testStorage, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        testStorage.clear();
        Storage.fruits.clear();
    }
}
