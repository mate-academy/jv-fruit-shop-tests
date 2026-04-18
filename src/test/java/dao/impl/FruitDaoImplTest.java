package dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDaoImpl fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void add_newFruit_ok() {
        fruitDao.add("banana", 100);
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(100, actual);
    }

    @Test
    void getQuantity_existingFruit_ok() {
        FruitStorage.fruits.put("apple", 50);
        int actual = fruitDao.getQuantity("apple");
        assertEquals(50, actual);
    }
}
