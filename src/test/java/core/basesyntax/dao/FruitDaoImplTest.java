package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

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

    @Test()
    public void update_Negative_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.update("apple", -10));
    }

    @Test()
    public void update_AmountFruitNull_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.update("apple", null));
    }

    @Test()
    public void update_FruitNameNull_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.update(null, 12));
    }

    @Test()
    public void update_NameAndAmountNull_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.update(null, null));
    }

    @Test
    public void getQuantity_Ok() {
        int exceptedQuantity = 15;
        Storage.fruits.put("apple", 15);
        int actualQuantity = fruitDao.getQuantity("apple");
        assertEquals(exceptedQuantity,actualQuantity);
    }

    @Test
    public void getQuantityDoesExist_notOk() {
        Storage.fruits.put("apple", 1);
        assertThrows(RuntimeException.class, () -> fruitDao.getQuantity("tomato"));
    }
}
