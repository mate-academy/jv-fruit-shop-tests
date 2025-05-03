package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitDaoTest {
    private static final String FIRST_VALID_FRUIT = "fruit1";
    private static final Integer FIRST_VALID_FRUIT_QUANTITY = 10;
    private static final String SECOND_VALID_FRUIT = "fruit2";
    private static final Integer SECOND_VALID_FRUIT_QUANTITY = 50;
    private static final String UNDEFINED_FRUIT = "fruit5";
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }

    @Test
    void add_ValidData_Ok() {
        fruitDao.add(FIRST_VALID_FRUIT, FIRST_VALID_FRUIT_QUANTITY);
        fruitDao.add(SECOND_VALID_FRUIT, SECOND_VALID_FRUIT_QUANTITY);
        assertEquals(50, fruitDao.get(SECOND_VALID_FRUIT));
    }

    @Test
    void update_ValidData_Ok() {
        fruitDao.add(FIRST_VALID_FRUIT, FIRST_VALID_FRUIT_QUANTITY);
        fruitDao.update(FIRST_VALID_FRUIT, 80);
        assertEquals(80, fruitDao.get(FIRST_VALID_FRUIT));
    }

    @Test
    void get_ValidData_Ok() {
        fruitDao.add(FIRST_VALID_FRUIT, FIRST_VALID_FRUIT_QUANTITY);
        assertEquals(fruitDao.get(FIRST_VALID_FRUIT), fruitDao.get(FIRST_VALID_FRUIT));
    }

    @Test
    void get_InvalidData_NotOk() {
        fruitDao.add(FIRST_VALID_FRUIT, FIRST_VALID_FRUIT_QUANTITY);
        assertNull(fruitDao.get(UNDEFINED_FRUIT));
    }
}
