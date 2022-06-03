package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void add_NullFruitName_NotOk() {
        fruitDao.add(null, 10);
    }

    @Test
    public void add_BlankFruitName_NotOk() {
        fruitDao.add(" ", 10);
    }

    @Test
    public void add_NullAmountValue_NotOk() {
        fruitDao.add("banana", null);
    }

    @Test
    public void add_NullData_NotOk() {
        fruitDao.add(null, null);
    }

    @Test
    public void add_NegativeAmount_NotOk() {
        fruitDao.add("apple", -10);
    }

    @Test
    public void getQuantity_Ok() {
        int expected = 20;
        Storage.fruits.put("apple", 20);
        int actual = fruitDao.getQuantity("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_NotOk() {
        Storage.fruits.put("apple", 40);
        Storage.fruits.put("banana", 25);
        try {
            fruitDao.getQuantity("orange");
        } catch (Exception e) {
            assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void add_Fruit_Ok() {
        int fruitNumber = 15;
        fruitDao.add("banana", fruitNumber);
        int actual = Storage.fruits.get("banana");
        assertEquals(fruitNumber, actual);
    }

    @Test
    public void add_TwoFruits_OK() {
        fruitDao.add("apple", 12);
        fruitDao.add("banana", 18);
        int actualApple = Storage.fruits.get("apple");
        int actualBanana = Storage.fruits.get("banana");
        int expectedAmountApple = 12;
        int expectedAmountBanana = 18;
        assertEquals(expectedAmountApple, actualApple);
        assertEquals(expectedAmountBanana, actualBanana);
    }
}
