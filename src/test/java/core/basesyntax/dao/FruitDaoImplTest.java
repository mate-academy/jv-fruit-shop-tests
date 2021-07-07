package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void putToDb_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 120);
        fruitDao.put(new Fruit("cherry"), 120);
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test
    public void getFromDb_Ok() {
        fruitDao.put(new Fruit("cherry"), 120);
        Integer expected = 120;
        Integer actual = fruitDao.get(new Fruit("cherry"));
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_Ok() {
        fruitDao.put(new Fruit("cherry"), 120);
        fruitDao.put(new Fruit("banana"), 81);
        fruitDao.put(new Fruit("apple"), 1);
        Set<Map.Entry<Fruit, Integer>> actual = fruitDao.getAll();
        Set<Map.Entry<Fruit, Integer>> expected = Storage.fruitStorage.entrySet();
        assertEquals(expected, actual);
    }
}
