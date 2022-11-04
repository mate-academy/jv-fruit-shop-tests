package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Map<String,Integer> expectedStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        expectedStorage = new HashMap<>();
        expectedStorage.put("banana",555);
        expectedStorage.put("apple",111);
    }

    @Test
    public void putAndGetIs_Ok() {
        fruitDao.put("apple",111);
        Assert.assertEquals(expectedStorage.get("apple"),fruitDao.get("apple"));
    }

    @Test
    public void mergeIs_Ok() {
        fruitDao.merge("apple",111,Integer::sum);
        Assert.assertEquals(expectedStorage.get("apple"),fruitDao.get("apple"));
    }

    @Test
    public void getAllIs_Ok() {
        fruitDao.put("banana",555);
        fruitDao.put("apple", 111);
        Assert.assertEquals(expectedStorage,fruitDao.getAll());
    }

    @After
    public void clear() {
        FruitStorage.storage.clear();
    }
}
