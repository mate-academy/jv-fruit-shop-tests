package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private static final int VALID_AMOUNT = 15;
    private static final int INVALID_AMOUNT = -1;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUp() {
        fruitDao.set("apple", VALID_AMOUNT);
    }

    @Test
    void setInvalidAmount_Not_Ok() {
        assertThrows(RuntimeException.class, () -> fruitDao.set("orange", INVALID_AMOUNT));
    }

    @Test
    void setValidAmount_Ok() {
        fruitDao.set("banana", VALID_AMOUNT);
        Integer actual = FruitDaoImpl.getFruits().get("banana");
        assertEquals(VALID_AMOUNT, actual);
    }

    @Test
    void addInvalidAmount_notOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.add("apple", INVALID_AMOUNT));
    }

    @Test
    void addValidAmount_Ok() {
        fruitDao.add("apple", VALID_AMOUNT);
        Integer actual = FruitDaoImpl.getFruits().get("apple");
        assertEquals(VALID_AMOUNT + VALID_AMOUNT, actual);
    }

    @Test
    void removeInvalidAmount_notOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.remove("apple", Integer.MAX_VALUE));
    }

    @Test
    void removeValidAmount_Ok() {
        fruitDao.remove("apple", VALID_AMOUNT);
        Integer actual = FruitDaoImpl.getFruits().get("apple");
        assertEquals(0, actual);
    }
}
