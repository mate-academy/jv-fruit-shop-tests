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
        int fruitAppleNumber = 37;
        fruitDao.update("apple", fruitAppleNumber);
        int actualAppleNumber = Storage.fruits.get("apple");
        assertEquals(fruitAppleNumber,actualAppleNumber);
    }

    @Test
    public void update_TwoFruit_Ok() {
        int fruitAppleNumber = 95;
        int fruitBananaNumber = 43;
        fruitDao.update("apple",fruitAppleNumber);
        fruitDao.update("banana", fruitBananaNumber);
        int actualAppleNumber = Storage.fruits.get("apple");
        int actualBananaNumber = Storage.fruits.get("banana");
        assertEquals(fruitAppleNumber,actualAppleNumber);
        assertEquals(fruitBananaNumber,actualBananaNumber);
    }

    @Test
    public void update_ZeroFruit_Ok() {
        int fruitService = 0;
        fruitDao.update("apple", 0);
        int actualFruitNumber = Storage.fruits.get("apple");
        assertEquals(fruitService,actualFruitNumber);
    }

    @Test(expected = RuntimeException.class)
    public void update_Negative_NotOk() {
        fruitDao.update("apple", -10);
    }

    @Test(expected = RuntimeException.class)
    public void update_AmountFruitNull_NotOk() {
        fruitDao.update("apple", null);
    }

    @Test(expected = RuntimeException.class)
    public void update_FruitNameNull_NotOk() {
        fruitDao.update(null, 12);
    }

    @Test(expected = RuntimeException.class)
    public void update_NameAndAmountNull_NotOk() {
        fruitDao.update(null, null);
    }

    @Test
    public void getQuantity_Ok() {
        int exceptedQuantity = 15;
        Storage.fruits.put("apple", 15);
        int actualQuantity = fruitDao.getQuantity("apple");
        assertEquals(exceptedQuantity,actualQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantityDoesExist_notOk() {
        Storage.fruits.put("apple", 1);
        fruitDao.getQuantity("tomato");
    }

    @Test(expected = RuntimeException.class)
    public void getQuantityRequestNull_notOk() {
        Storage.fruits.put("apple", 1);
        fruitDao.getQuantity(null);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantityRequestNumber_notOk() {
        Storage.fruits.put("apple", 1);
        fruitDao.getQuantity("1");
    }

    @Test(expected = RuntimeException.class)
    public void getQuantityRequestChar_notOk() {
        Storage.fruits.put("apple", 1);
        fruitDao.getQuantity("$");
    }
}
