package core.basesyntax.dao;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class FruitDaoImplTest {
    private static FruitDao fruitDao = new FruitDaoImpl();
    private static Map<Fruit, Integer> testStorage = new HashMap<>();
    private static Fruit BANANA = new Fruit("Banana");
    private static Fruit APPLE = new Fruit("Apple");
    private static Fruit ORANGE = new Fruit("Orange");

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
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    public void getRemainFruit_ok() {
        assertEquals(Integer.valueOf(100),
                fruitDao.getQuantity(BANANA));
        assertEquals(Integer.valueOf(150),
                fruitDao.getQuantity(APPLE));
        assertEquals(Integer.valueOf(200),
                fruitDao.getQuantity(ORANGE));
    }

    @Test
    public void getStorage_ok() {
        assertEquals(testStorage, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        testStorage.clear();
        Storage.fruits.clear();
    }
}
