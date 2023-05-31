package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private FruitDao fruitDao;
    private Map<String, Integer> expectedMap;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        expectedMap = new HashMap<>();
        fruitDao.put("banana", 100);
        fruitDao.put("apple", 150);
        expectedMap.put("banana", 100);
        expectedMap.put("apple", 150);
    }

    @Test
    public void fruitDao_checkedEntry() {
        Assert.assertEquals(fruitDao.getEntries(), expectedMap.entrySet());
    }

    @Test
    public void fruitDao_checkedAddition_Ok() {
        expectedMap.put("banana", 250);
        expectedMap.put("apple", 150);
        fruitDao.addition("banana", 50);
        fruitDao.addition("banana", 25);
        fruitDao.addition("banana", 75);
        fruitDao.addition("apple", 0);
        Assert.assertEquals(fruitDao.getEntries(), expectedMap.entrySet());
    }

    @Test
    public void fruitDao_checkedSubtract_Ok() {
        expectedMap.put("banana", 10);
        expectedMap.put("apple", 150);
        fruitDao.subtract("banana", 10);
        fruitDao.subtract("banana", 20);
        fruitDao.subtract("banana", 60);
        fruitDao.subtract("apple", 0);
        Assert.assertEquals(fruitDao.getEntries(), expectedMap.entrySet());
    }

    @Test(expected = RuntimeException.class)
    public void fruitDao_checkedSubtract_NotOk() {
        fruitDao.subtract("banana", 101);
        fruitDao.subtract("apple", 151);
    }
}
