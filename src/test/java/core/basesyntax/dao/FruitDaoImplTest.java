package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Map<String, Integer> expectedStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void put_isOk() {
        expectedStorage = Map.of("apple", 111);
        fruitDao.put("apple", 111);
        Assert.assertEquals(FruitStorage.storage, expectedStorage);
    }

    @Test
    public void get_isOk() {
        expectedStorage = Map.of("banana", 555);
        FruitStorage.storage.put("banana", 555);
        Assert.assertEquals(FruitStorage.storage, expectedStorage);
    }

    @Test
    public void merge_isOk() {
        expectedStorage = Map.of("apple", 111);
        fruitDao.merge("apple", 111, Integer::sum);
        Assert.assertEquals(FruitStorage.storage, expectedStorage);
    }

    @Test
    public void getAll_isOk() {
        expectedStorage = Map.of("banana", 555, "apple", 111);
        fruitDao.put("banana", 555);
        fruitDao.put("apple", 111);
        Assert.assertEquals(expectedStorage, fruitDao.getAll());
    }

    @After
    public void clear() {
        FruitStorage.storage.clear();
    }
}
