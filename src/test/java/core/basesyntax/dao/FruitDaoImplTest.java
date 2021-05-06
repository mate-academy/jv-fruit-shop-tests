package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.fruitmodel.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit FRUIT = new Fruit("apple");
    private static final Integer QUANTITY = 5;
    private static FruitDao fruitDao;
    private static Map<Fruit, Integer> expectedMap;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        expectedMap = new HashMap<>();
    }

    @Test
    public void save_Ok() {
        expectedMap.put(FRUIT, QUANTITY);
        fruitDao.save(FRUIT, QUANTITY);
        assertEquals(expectedMap, FruitStorage.fruitStorage);
    }

    @Test
    public void get_Ok() {
        expectedMap.put(FRUIT, QUANTITY);
        fruitDao.save(FRUIT, QUANTITY);
        Integer expected = expectedMap.get(FRUIT);
        Integer actual = fruitDao.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_Ok() {
        expectedMap.put(FRUIT, QUANTITY);
        fruitDao.save(FRUIT, QUANTITY);
        assertEquals(expectedMap, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
