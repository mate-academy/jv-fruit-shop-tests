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
    public void updateOneFruit_Ok() {
        fruitDao.update("banana", 33);
        int actual = Storage.fruits.get("banana");
        assertEquals(33, actual);
    }

    @Test
    public void updateTwoFruits_Ok() {
        fruitDao.update("banana", 13);
        int actualBanana = Storage.fruits.get("banana");
        fruitDao.update("apple", 18);
        int actualApple = Storage.fruits.get("apple");
        assertEquals(13, actualBanana);
        assertEquals(18, actualApple);
    }

    @Test(expected = RuntimeException.class)
    public void updateNullValue_NotOk() {
        fruitDao.update(null, null);
        fruitDao.update(null, 41);
        fruitDao.update("banana", null);
    }

    @Test(expected = RuntimeException.class)
    public void updateNegative_NotOk() {
        fruitDao.update("banana", -18);
    }

    @Test
    public void updateZeroAmount_Ok() {
        fruitDao.update("banana", 0);
    }

    @Test
    public void getQuantity_Ok() {
        fruitDao.update("apple", 23);
        int actual = fruitDao.getQuantity("apple");
        assertEquals(23, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_DoesntExist_NotOk() {
        fruitDao.update("apple", 45);
        fruitDao.update("banana", 54);
        fruitDao.getQuantity("pineapple");
    }
}
