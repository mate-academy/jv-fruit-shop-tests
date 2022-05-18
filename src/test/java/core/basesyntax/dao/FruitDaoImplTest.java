package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class FruitDaoImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void update_OneFruit_Ok() {
        int fruitNumber = 33;
        fruitDao.update("banana", fruitNumber);
        int actual = Storage.fruits.get("banana");
        assertEquals(fruitNumber, actual);
    }

    @Test
    public void update_TwoFruits_Ok() {
        fruitDao.update("banana", 13);
        fruitDao.update("apple", 18);
        int actualBanana = Storage.fruits.get("banana");
        int actualApple = Storage.fruits.get("apple");
        int expectedBanana = 13;
        int expectedApple = 18;
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullValue_NotOk() {
        fruitDao.update("banana", null);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullKey_NotOk() {
        fruitDao.update(null, 6);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullData_NotOk() {
        fruitDao.update(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void update_Negative_NotOk() {
        fruitDao.update("banana", -18);
    }

    @Test
    public void update_ZeroAmount_Ok() {
        int expected = 0;
        fruitDao.update("banana", 0);
        int actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_Ok() {
        int expected = 23;
        Storage.fruits.put("apple", 23);
        int actual = fruitDao.getQuantity("apple");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_DoesntExist_NotOk() {
        Storage.fruits.put("apple", 45);
        Storage.fruits.put("banana", 54);
        fruitDao.getQuantity("pineapple");
    }
}
