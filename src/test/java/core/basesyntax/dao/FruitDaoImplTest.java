package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int AMOUNT = 20;
    private static FruitDao fruitDao = new FruitDaoImpl();

    @Test
    void addFruit_correctData_Ok() {
        fruitDao.addFruit(FRUIT_NAME, AMOUNT);
        assertEquals(AMOUNT, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void addFruit_repeatingData_Ok() {
        fruitDao.addFruit(FRUIT_NAME, AMOUNT);
        int newAmount = 40;
        fruitDao.addFruit(FRUIT_NAME, newAmount);
        assertEquals(newAmount, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void addFruit_negativeAmount_NotOk() {
        int negativeAmount = -AMOUNT;
        assertThrows(RuntimeException.class, () -> fruitDao.addFruit(FRUIT_NAME, negativeAmount));
    }

    @Test
    void get_correctData_Ok() {
        fruitDao.addFruit(FRUIT_NAME, AMOUNT);
        int expected = Storage.storage.get(FRUIT_NAME);
        int actual = fruitDao.get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
