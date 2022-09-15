package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static FruitDao fruitDao;

    @BeforeClass
    public static void before() {
        fruitDao = new FruitDaoImpl();

    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void add_Ok() {
        final Integer expectedApple = 10;
        final Integer expectedBanana = 15;
        fruitDao.add(APPLE, 10);
        fruitDao.add(BANANA, 15);
        assertEquals(expectedApple, Storage.fruitStorage.get(APPLE));
        assertEquals(expectedBanana, Storage.fruitStorage.get(BANANA));
    }

    @Test
    public void get_Ok() {
        final Integer expectedApple = 20;
        final Integer expectedBanana = 25;
        Storage.fruitStorage.put(APPLE, 20);
        Storage.fruitStorage.put(BANANA, 25);
        assertEquals(expectedApple, fruitDao.get(APPLE));
        assertEquals(expectedBanana, fruitDao.get(BANANA));
    }

    @Test
    public void getStorageState_Ok() {
        Map<String, Integer> expectedMap = new TreeMap<>();
        expectedMap.put(APPLE, 45);
        expectedMap.put(BANANA, 25);
        Storage.fruitStorage.put(APPLE, 45);
        Storage.fruitStorage.put(BANANA, 25);
        assertEquals(expectedMap, fruitDao.getStorageState());
    }
}
