package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;

public class FruitDaoImplTest {
    private FruitDao fruitDao;
    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.getAll().clear();
    }

@Test
    void add_validFruit_ok() {
        fruitDao.add("apple",10);
        assertEquals(10,fruitDao.getQuantity("apple"));

    }

@Test
    void add_NegativeBalance_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            fruitDao.add("apple", -5);
        });
    }
}
