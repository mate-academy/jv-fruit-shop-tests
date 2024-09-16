package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.fruits.clear();
    }

    @Test
    void addAndGetFruit_Ok() {
        fruitDao.add("banana", 10);
        assertEquals(10, fruitDao.get("banana"));
    }

    @Test
    void addAndGetMultipleFruits_Ok() {
        fruitDao.add("banana", 10);
        fruitDao.add("apple", 20);
        assertEquals(10, fruitDao.get("banana"));
        assertEquals(20, fruitDao.get("apple"));
    }
}
