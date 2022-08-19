package core.basesyntax.dao;

import core.basesyntax.db.FruitsStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitDaoImplTest {
    private FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void afterEachTest() {
        FruitsStorage.fruits.clear();
    }

    @Test
    public void getFruitsStorageWithDifferentFruits_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        Map<String, Integer> actualResult = fruitDao.getStorageData();
        Map<String, Integer> expectedResult = FruitsStorage.fruits;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getFruitsStorageEmpty_Ok() {
        Map<String, Integer> actualResult = fruitDao.getStorageData();
        HashMap<String, Integer> expectedResult = new HashMap<>();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getQuantityForApple_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        Integer actualResult = fruitDao.getQuantityForFruit("apple");
        Integer expectedResult = 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void setQuantityForOrange_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        fruitDao.setQuantityForFruit("orange", 120);
        Integer actualResult = FruitsStorage.fruits.get("orange");
        Integer expectedResult = 120;
        Assert.assertEquals(expectedResult, actualResult);
    }
}
