package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitQuantityDaoImplTest {
    private static final int FIRST_VALID_QUANTITY = 20;
    private static final int SECOND_VALID_QUANTITY = 150;
    private static final String FIRST_VALID_FRUIT_NAME = "apple";
    private static final String SECOND_VALID_FRUIT_NAME = "banana";
    private static FruitQuantityDao fruitQuantityDao;

    @BeforeAll
    static void beforeAll() {
        fruitQuantityDao = new FruitQuantityDaoImpl();
    }

    @Test
    void add_oneValidFruit_ok() {
        fruitQuantityDao.add(FIRST_VALID_FRUIT_NAME, FIRST_VALID_QUANTITY);
        assertEquals(FIRST_VALID_QUANTITY, fruitQuantityDao.get(FIRST_VALID_FRUIT_NAME));
    }

    @Test
    void get_oneValidFruit_ok() {
        fruitQuantityDao.add(FIRST_VALID_FRUIT_NAME, FIRST_VALID_QUANTITY);
        int actualQuantity = fruitQuantityDao.get(FIRST_VALID_FRUIT_NAME);
        assertEquals(FIRST_VALID_QUANTITY, actualQuantity);
    }

    @Test
    void getALl_emptyStorage_ok() {
        Map<String, Integer> fruitsQuantity = fruitQuantityDao.getAll();
        assertTrue(fruitsQuantity.isEmpty());
    }

    @Test
    void getALl_notEmptyStorage_ok() {
        fruitQuantityDao.add(FIRST_VALID_FRUIT_NAME, FIRST_VALID_QUANTITY);
        fruitQuantityDao.add(SECOND_VALID_FRUIT_NAME, SECOND_VALID_QUANTITY);
        Map<String, Integer> fruitsQuantity = fruitQuantityDao.getAll();
        assertEquals(FIRST_VALID_QUANTITY, fruitsQuantity.get(FIRST_VALID_FRUIT_NAME));
        assertEquals(SECOND_VALID_QUANTITY, fruitsQuantity.get(SECOND_VALID_FRUIT_NAME));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitQuantity.clear();
    }
}
