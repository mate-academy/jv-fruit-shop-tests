package core.basesyntax.dao;

import core.basesyntax.db.FruitsStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @After
    public void afterEachTest() {
        FruitsStorage.fruits.clear();
    }

    @Test
    public void getStorageData_notEmpty_Ok() {
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("banana", 100);
        expectedResult.put("orange", 70);
        expectedResult.put("apple", 50);
        for (Map.Entry<String, Integer> entry : expectedResult.entrySet()) {
            FruitsStorage.fruits.put(entry.getKey(), entry.getValue());
        }
        Map<String, Integer> actualResult = fruitDao.getStorageData();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStorageData_empty_Ok() {
        Map<String, Integer> actualResult = fruitDao.getStorageData();
        HashMap<String, Integer> expectedResult = new HashMap<>();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getQuantity_apple_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        Integer actualResult = fruitDao.getQuantityForFruit("apple");
        Integer expectedResult = 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void setQuantity_orange_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        fruitDao.setQuantityForFruit("orange", 120);
        Integer actualResult = FruitsStorage.fruits.get("orange");
        Integer expectedResult = 120;
        Assert.assertEquals(expectedResult, actualResult);
    }
}
