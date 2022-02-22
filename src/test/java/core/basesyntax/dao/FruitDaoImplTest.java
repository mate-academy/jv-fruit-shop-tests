package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final int EXPECTED_SIZE_FOR_EXISTS = 1;
    private static final int EXPECTED_SIZE_FOR_NOT_EXISTS = 0;
    private static final int AMOUNT = 30;
    private static final String FRUIT = "apple";
    private static Storage storage;
    private final FruitDao<String, Integer> fruitDao;
    private final Map<String, Integer> expectedStorage;

    public FruitDaoImplTest() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        expectedStorage = new HashMap<>();
    }

    @Test(expected = RuntimeException.class)
    public void addInStorage_notOk() {
        fruitDao.add(null, null);
    }

    @Test
    public void addInStorage_Ok() {
        fruitDao.add(FRUIT, AMOUNT);
        assertEquals(EXPECTED_SIZE_FOR_EXISTS, storage.getFruitsStorage().size());
    }

    @Test
    public void remove_Ok() {
        fruitDao.add(FRUIT, AMOUNT);
        fruitDao.remove(FRUIT,AMOUNT);
        assertEquals(EXPECTED_SIZE_FOR_NOT_EXISTS, storage
                .getFruitsStorage().get(FRUIT).intValue());
    }

    @Test
    public void getFruitCount_Ok() {
        fruitDao.add(FRUIT, AMOUNT);
        assertEquals(AMOUNT, storage.getFruitsStorage().get(FRUIT).intValue());
    }

    @Test
    public void getAll_Ok() {
        fruitDao.add(FRUIT, AMOUNT);
        expectedStorage.put(FRUIT,AMOUNT);
        assertEquals(expectedStorage.entrySet(), fruitDao.getAll().entrySet());
    }

    @AfterClass
    public static void afterClass() {
        storage.getFruitsStorage().clear();
    }
}
