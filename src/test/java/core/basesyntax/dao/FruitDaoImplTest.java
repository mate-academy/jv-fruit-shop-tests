package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String PEAR = "pear";
    private static final String CHERRY = "cherry";
    private static final int INITIAL_APPLE_QUANTITY = 5;
    private static final int UPDATED_APPLE_QUANTITY = 20;
    private static final int INITIAL_BANANA_QUANTITY = 10;
    private static final int INITIAL_PEAR_QUANTITY = 15;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.fruits.clear();
    }

    @Test
    void addFruit_Ok() {
        fruitDao.add(APPLE, INITIAL_APPLE_QUANTITY);
        assertEquals(INITIAL_APPLE_QUANTITY, fruitDao.get(APPLE),
                "Expected " + INITIAL_APPLE_QUANTITY + " apples in storage");
    }

    @Test
    void getFruitQuantity_Ok() {
        fruitDao.add(BANANA, INITIAL_BANANA_QUANTITY);
        assertEquals(INITIAL_BANANA_QUANTITY, fruitDao.get(BANANA),
                "Expected " + INITIAL_BANANA_QUANTITY + " bananas in storage");
    }

    @Test
    void updateFruitQuantity_Ok() {
        fruitDao.add(APPLE, INITIAL_PEAR_QUANTITY);
        fruitDao.add(APPLE, UPDATED_APPLE_QUANTITY);
        assertEquals(UPDATED_APPLE_QUANTITY,fruitDao.get(APPLE),
                "Expected " + UPDATED_APPLE_QUANTITY + " apples in storage");
    }

    @Test
    void addMultipleFruits_Ok() {
        fruitDao.add(APPLE, INITIAL_APPLE_QUANTITY);
        assertEquals(INITIAL_APPLE_QUANTITY, fruitDao.get(APPLE),
                "Expected " + INITIAL_APPLE_QUANTITY + " apples in storage");
        fruitDao.add(BANANA, INITIAL_BANANA_QUANTITY);
        assertEquals(INITIAL_BANANA_QUANTITY, fruitDao.get(BANANA),
                "Expected " + INITIAL_BANANA_QUANTITY + " bananas in storage");
        fruitDao.add(PEAR, INITIAL_PEAR_QUANTITY);
        assertEquals(INITIAL_PEAR_QUANTITY, fruitDao.get(PEAR),
                "Expected " + INITIAL_PEAR_QUANTITY + " pears in storage");
    }

    @Test
    void getNonExistentFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitDao.get(CHERRY);
        });
    }
}
