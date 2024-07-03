package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitDaoTest {
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
        fruitDao.add("fruit1", 10);
        fruitDao.add("fruit2", 50);
        assertEquals(50, fruitDao.get("fruit2"));
    }

    @Test
    void update_ValidData_Ok() {
        fruitDao.add("fruit1", 10);
        fruitDao.update("fruit1", 80);
        assertEquals(80, fruitDao.get("fruit1"));
    }

    @Test
    void get_ValidData_Ok() {
        fruitDao.add("fruit1", 10);
        assertEquals(fruitDao.get("fruit1"), fruitDao.get("fruit1"));
    }

    @Test
    void get_InvalidData_NotOk() {
        fruitDao.add("fruit1", 10);
        assertNull(fruitDao.get("fruit5"));
    }
}
