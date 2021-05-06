package core.basesyntax.dao;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static final Fruit FRUIT = new Fruit("banana");
    private static final int QUANTITY = 25;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void fruitDaoImplTest_add_Ok() {
        Map<Fruit, Integer> fruitsDataBase = new HashMap<>();
        fruitsDataBase.put(FRUIT, QUANTITY);
        fruitDao.add(FRUIT, QUANTITY);
        Assert.assertEquals(fruitsDataBase, fruitDao.getAll());
    }

    @Test
    public void fruitDaoImplTest_get_Ok() {
        Storage.getFruitsDataBase().put(FRUIT, QUANTITY);
        int expected = Storage.getFruitsDataBase().get(FRUIT);
        int actual = fruitDao.get(FRUIT).orElse(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitDaoImplTest_getAll_Ok() {
        Storage.getFruitsDataBase().put(FRUIT, QUANTITY);
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(FRUIT, QUANTITY);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getFruitsDataBase().clear();
    }
}
