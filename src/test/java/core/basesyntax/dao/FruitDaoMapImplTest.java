package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoMapImplTest {
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String BANANA = "banana";
    private static FruitDao fruitDao;

    @BeforeClass
    public static void setFruitDao() {
        fruitDao = new FruitDaoMapImpl();
    }

    @Before
    public void cleanMapDb() {
        Storage.storage.clear();
    }

    @Test
    public void add_storageContainsData_isOk() {
        Fruit apple = new Fruit("apple", 50);
        fruitDao.save(apple);
        Optional<Fruit> fruit = fruitDao.get(apple.getName());
        assertEquals(apple, fruit.get());
    }

    @Test
    public void get_storageContainsNoData_isEmpty() {
        Optional<Fruit> fruit = fruitDao.get(ORANGE);
        assertEquals(Optional.empty(), fruit);
    }

    @Test
    public void getAll_storageContainsData_isOk() {
        Fruit apple = new Fruit(APPLE, 50);
        Fruit orange = new Fruit(ORANGE, 40);
        Fruit banana = new Fruit(BANANA, 10);
        fruitDao.save(apple);
        fruitDao.save(orange);
        fruitDao.save(banana);

        List<Fruit> all = fruitDao.getAll();
        assertTrue(all.contains(apple));
        assertTrue(all.contains(orange));
        assertTrue(all.contains(banana));
    }
}
